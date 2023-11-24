package Proposal2;

import com.apple.foundationdb.KeySelector;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.tuple.Tuple;
import com.apple.foundationdb.tuple.Tuples;
import com.google.common.base.Charsets;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import com.wavefront.common.BytesKey;
import com.wavefront.common.PatternMatch;
import org.apache.commons.lang.StringUtils;
import sldb.IdType;
import sldb.TupleConstants;
import sldb.ids.IdManager;
import sldb.ids.PerCustomerIdManager;
import sldb.ids.PerCustomerIdManagers;
import wavefront.fdb.Counters;
import wavefront.fdb.MaxRangeIterator;
import wavefront.fdb.util.KeyStartsWith;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static Proposal2.Executor.db;
import static com.apple.foundationdb.KeySelector.*;
import static com.apple.foundationdb.tuple.ByteArrayUtil.strinc;
import static com.apple.foundationdb.tuple.Tuples.pack;
import static com.google.common.collect.Lists.newArrayList;

public class ReadSourceID {

    private final static IdManager CUSTOMER_ID_MANAGER = IdManager.createCustomerIdManager(TupleConstants.CUSTOMER_TYPE, db);

    public final static PerCustomerIdManagers ID_MANAGERS =
            new PerCustomerIdManagers(c -> new PerCustomerIdManager(c, CUSTOMER_ID_MANAGER, db, null));

    public List<String> listHostsStartingAfter(@Nullable String startingHostName, int max, boolean sortAscending,
                                               @Nullable String pattern, String customer) {

        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        Tuple hostIdTuple = TupleConstants.HOST_TYPE.add(customerId);
        KeySelector startKey;
        KeySelector endKey;
        if (startingHostName == null) {
            startingHostName = "";
        }

        if (sortAscending) {
            startKey = firstGreaterThan(Tuples.pack(hostIdTuple,
                    startingHostName.getBytes(StandardCharsets.UTF_8)));
            endKey = lastLessThan(strinc(hostIdTuple.pack())).add(1);
        } else {
            startKey = firstGreaterThan(hostIdTuple.pack());
            if (StringUtils.isBlank(startingHostName)) {
                endKey = lastLessThan(strinc(hostIdTuple.pack())).add(1);
            } else {
                endKey = lastLessOrEqual(Tuples.pack(hostIdTuple,
                        startingHostName.getBytes(StandardCharsets.UTF_8)));
                if (!KeyStartsWith.startsWith(hostIdTuple.pack(), endKey.getKey())) {
                    return new ArrayList<>();
                }
            }
        }
        Predicate<String> hostFilter = Strings.isNullOrEmpty(pattern) ?
                Predicates.alwaysTrue() : PatternMatch.buildPredicate(pattern, true);
        return listHosts(startKey, endKey, max, !sortAscending, hostFilter, customer);
    }

    private List<String> listHosts(KeySelector startKey, KeySelector endKey, int max,
                                   boolean descending, Predicate<String> hostPredicate, String customer) {

        IdManager sourceIdManager = ID_MANAGERS.get(customer).get(IdType.HOST);
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        Tuple hostIdTuple = TupleConstants.HOST_TYPE.add(customerId);
        List<String> toReturn = newArrayList();
        int length = Tuples.pack(hostIdTuple).length;
        Iterator<KeyValue> hostsIterator = new MaxRangeIterator(startKey, endKey, 10000,
                new Counters(), db, descending);
        while (hostsIterator.hasNext()) {
            if (toReturn.size() >= max) break;
            KeyValue next = hostsIterator.next();
            String host = new String(Tuples.getBytes(length, next.getKey(), 0, null, null), Charsets.UTF_8);
            if (!hostPredicate.test(host))
                continue;
            byte[] hostId = sourceIdManager.getIdB(host);
            if (hostId == null) continue;
            toReturn.add(host);
        }
        if (descending) {
            toReturn.sort(Collections.reverseOrder());
        } else {
            Collections.sort(toReturn);
        }
        return toReturn;
    }

    public long latestTimeStampForSourceFromHMT(String customer, String source) {
        long latestTimestamp = -1;
        long earliestTimestamp = Long.MAX_VALUE;

        IdManager sourceIdManager = ID_MANAGERS.get(customer).get(IdType.HOST);

        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        CompletableFuture<BytesKey> customerIdKey = ID_MANAGERS.get(customer).get(IdType.CUSTOMER).getIdAsync(customer);

        CompletableFuture<BytesKey> sourceIdKey = sourceIdManager.getIdAsync(source, true);
        System.out.println("customerKey : " + customerIdKey.join().toString() + ", sourceKey  : " + sourceIdKey.join().toString());
        byte[] sourceId = sourceIdManager.getIdIfPresent(source);
        KeySelector start = KeySelector.firstGreaterOrEqual(pack(TupleConstants.
                getTuples(IdType.METRIC).getEntityHostTrieTuple(), customerId, sourceId));
        KeySelector end = KeySelector.firstGreaterOrEqual(strinc(pack(TupleConstants.
                getTuples(IdType.METRIC).getEntityHostTrieTuple(), customerId, sourceId)));
        System.out.println("Start KeySelector : " + start + ", End KeySelector : " + end);


        MaxRangeIterator hmtIterator = new MaxRangeIterator(start, end, Integer.MAX_VALUE, new Counters(), db);
        while (hmtIterator.canGetNext()) {
            KeyValue kv = hmtIterator.next();
            String path;
            try {
                path = Tuples.getString(0, kv.getKey(), 3, null);
//                System.out.println(path);
            } catch (RuntimeException e) {
                // If we can't decode the key, just skip it.
                continue;
            }
//            System.out.print(kv + ", ");
            long timestamp = Longs.fromByteArray(kv.getValue());
            latestTimestamp = Math.max(timestamp, latestTimestamp);
            earliestTimestamp = Math.min(timestamp, earliestTimestamp);
        }
        System.out.println("latest Timestamp : " + latestTimestamp + ", earliest Timestamp : " + earliestTimestamp);
        return latestTimestamp;
    }

    public boolean hmi(String customer, String source) {
        IdManager sourceIdManager = ID_MANAGERS.get(customer).get(IdType.HOST);
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        CompletableFuture<BytesKey> customerIdKey = ID_MANAGERS.get(customer).get(IdType.CUSTOMER).getIdAsync(customer);
        System.out.println(customerIdKey.join().toString());
        CompletableFuture<BytesKey> sourceIdKey = sourceIdManager.getIdAsync(source, true);
        try {
            System.out.println("customerKey : " + customerIdKey.join().toString() + ", sourceKey  : " + sourceIdKey.join().toString());
        } catch (NullPointerException e) {
            System.out.println("Source doesn't exist in SSD");
        }
        byte[] sourceId = sourceIdManager.getIdIfPresent(source);
        KeySelector start = KeySelector.firstGreaterOrEqual(pack(TupleConstants.
                getTuples(IdType.METRIC).getHostToEntityIndexTuple(), customerId, sourceId));
        KeySelector end = KeySelector.firstGreaterOrEqual(strinc(pack(TupleConstants.
                getTuples(IdType.METRIC).getHostToEntityIndexTuple(), customerId, sourceId)));
        System.out.println("Start KeySelector : " + start + ", End KeySelector : " + end);

        MaxRangeIterator hmiIterator = new MaxRangeIterator(start, end, Integer.MAX_VALUE, new Counters(), db);

        KeySelector startObsolete = KeySelector.firstGreaterOrEqual(pack(TupleConstants.
                getTuples(IdType.METRIC).getObsoleteHostToEntityIndexTuple(), customerId, sourceId));
        KeySelector endObsolete = KeySelector.firstGreaterOrEqual(strinc(pack(TupleConstants.
                getTuples(IdType.METRIC).getObsoleteHostToEntityIndexTuple(), customerId, sourceId)));
        System.out.println("Start KeySelector : " + startObsolete + ", End KeySelector : " + endObsolete);

        MaxRangeIterator hmiObsoleteIterator = new MaxRangeIterator(startObsolete, endObsolete, Integer.MAX_VALUE, new Counters(), db);

//        while (hmtIterator.canGetNext()) {
//            KeyValue kv = hmtIterator.next();
//            String path;
//            try {
//                path = Tuples.getString(0, kv.getKey(), 3, null);
////                System.out.println(path);
//            } catch (RuntimeException e) {
//                // If we can't decode the key, just skip it.
//                continue;
//            }
////            System.out.print(kv + ", ");
//            long timestamp = Longs.fromByteArray(kv.getValue());
//            latestTimestamp = Math.max(timestamp, latestTimestamp);
//            earliestTimestamp = Math.min(timestamp, earliestTimestamp);
//        }
//        System.out.println("latest Timestamp : " + latestTimestamp + ", earliest Timestamp : " + earliestTimestamp);

        String h = hmiIterator.canGetNext() ? "has metrics" : "doesn't have metrics";
        String ho = hmiObsoleteIterator.canGetNext() ? "has obsolete metrics" : "doesn't have obsolete metrics";
        System.out.println(h + " , " + ho);
        return hmiIterator.canGetNext() || hmiObsoleteIterator.canGetNext();
    }

    boolean checkHostIdExisting(String customer, String source) {
        IdManager sourceIdManager = ID_MANAGERS.get(customer).get(IdType.HOST);
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        CompletableFuture<BytesKey> customerIdKey = ID_MANAGERS.get(customer).get(IdType.CUSTOMER).getIdAsync(customer);
        customerIdKey.join();
        CompletableFuture<BytesKey> sourceIdKey = sourceIdManager.getIdAsync(source, true);
        try {
            System.out.println("customerIdKey : " + customerIdKey.join().toString());
            System.out.println("sourceIdKey  : " + sourceIdKey.join().toString());
        } catch (NullPointerException e) {
            return false;
        }
        byte[] sourceId = sourceIdManager.getIdIfPresent(source);
        System.out.println(KeySelector.firstGreaterOrEqual(customerId) + " " + KeySelector.firstGreaterOrEqual(sourceId));
        return sourceId != null;
    }
}


