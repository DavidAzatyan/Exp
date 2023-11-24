package FDB;

import SourceCRUD.ReadDataFromTelemetry;
import avrobase.AvroBaseException;
import avrobase.Row;
import avrobase.SearchableAvroBase;
import avrobase.fdb.FDBAB;
import avrobase.fdb.FDBABAvrobaseSchemas;
import avrobase.fdb.FDBQuery;
import com.apple.foundationdb.KeySelector;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.tuple.ByteArrayUtil;
import com.apple.foundationdb.tuple.Tuple;
import com.apple.foundationdb.tuple.Tuples;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Charsets;
import com.google.common.primitives.Longs;
import com.sunnylabs.dao.machine.MachineDAO;
import com.wavefront.common.BytesKey;
import com.wavefront.common.Compressor;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.glassfish.jersey.internal.guava.Ordering;
import sldb.IdType;
import sldb.TupleConstants;
import sldb.ids.IdManager;
import sldb.ids.IdPatternScanner;
import sldb.ids.PerCustomerIdManager;
import sldb.ids.PerCustomerIdManagers;
import sldb.planner.idsolver.IdScanSolver;
import sldb.planner.idsolver.IdScanSolverFactory;
import wavefront.fdb.Counters;
import wavefront.fdb.DB;
import wavefront.fdb.MaxRangeIterator;
import wavefront.fdb.NamedTxDatabase;
import wavefront.fdb.util.Collecterator;
import wavefront.fdb.util.Unionator;
import wavefront.machine.TaggedSource;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static SourceCRUD.Executor.db;
import static SourceCRUD.ReadDataFromTelemetry.ID_MANAGERS;
import static com.apple.foundationdb.tuple.ByteArrayUtil.printable;
import static com.apple.foundationdb.tuple.ByteArrayUtil.strinc;
import static com.apple.foundationdb.tuple.Tuples.pack;
import static com.google.common.collect.Iterators.limit;
import static sunnylabs.data.DataUtils.makeKeyPrefix;
import static sunnylabs.data.DataUtils.next;

public class Exp {
    public static void main(String[] args) {
        String customer = "master";
        List<String> hostnames = new ReadDataFromTelemetry().listHostsStartingAfter("", Integer.MAX_VALUE, true, "",customer);
        System.out.println("Count of sources from Telemetry data : " + hostnames.size());
        System.out.println(hostnames);
        for (String hostname : hostnames) {
            System.out.println();
            System.out.println("Source : " + hostname);
            new Exp().hmi(db, customer, hostname);
            System.out.println();
        }

        System.exit(0);
    }


    public boolean hmi(NamedTxDatabase db, String customer, String source) {
        IdManager sourceIdManager = ID_MANAGERS.get(customer).get(IdType.HOST);
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        CompletableFuture<BytesKey> customerIdKey = ID_MANAGERS.get(customer).get(IdType.CUSTOMER).getIdAsync(customer);
        System.out.println(customerIdKey.join().toString());
        CompletableFuture<BytesKey> sourceIdKey = sourceIdManager.getIdAsync(source, true);
        System.out.println("customerKey : " + customerIdKey.join().toString() + ", sourceKey  : " + sourceIdKey.join().toString());
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

        String h = hmiIterator.canGetNext()  ? "has metrics" : "doesn't have metrics";
        String ho = hmiObsoleteIterator.canGetNext()  ? "has obsolete metrics" : "doesn't have obsolete metrics";
        System.out.println(h + " , " + ho);
        return hmiIterator.canGetNext() || hmiObsoleteIterator.canGetNext();
    }

    void trigrams(){
        IdType idType = IdType.HOST;
        Tuple t_mis = idType == IdType.HOST ?
                TupleConstants.HOST_INDEX.add(ID_MANAGERS.get("master").getCustomerId()).add("index") :
                TupleConstants.getTuples(idType).getIndexSegmentIndexTuple().add(ID_MANAGERS.get("master").getCustomerId());
        Tuple t_sti = idType == IdType.HOST ?
                TupleConstants.HOST_INDEX.add(ID_MANAGERS.get("master").getCustomerId()).add("trigram") :
                TupleConstants.getTuples(idType).getTrigramSegmentIndexTuple().add(ID_MANAGERS.get("master").getCustomerId());

        System.out.println(t_mis);
        System.out.println(t_sti);

        String prefixToCleanup = "aut";
//        byte[] startKey = makeOpenEndedKey(pack(t_mis, prefixToCleanup));
//        byte[] currentKey = startKey;
//
//        int indexTupleLength = pack(t_mis).length;
//        MaxRangeIterator iterator = new MaxRangeIterator(
//                KeySelector.firstGreaterThan(currentKey),
//                KeySelector.firstGreaterOrEqual(strinc(startKey)),
//                Integer.MAX_VALUE, new Counters(), db);
//        while (iterator.canGetNext()) {
//            KeyValue kv = iterator.next();
//            int[] offset = new int[1];
//            String seg = (String) Tuples.getObject(indexTupleLength, kv.getKey(), 0, null, offset);
//            System.out.println(seg);
//        }
        int batchSize = 25000;
        Set<String> trigramsToCleanup = new LinkedHashSet<>();
        int lastFetchSize = batchSize;
        long itemsScanned = 0;
        byte[] startKey = makeOpenEndedKey(pack(t_mis, prefixToCleanup));
        byte[] currentKey = startKey;
        int indexTupleLength = pack(t_mis).length;
        String lastValue = null;
        long uniqueSegments = 0;
        while (lastFetchSize == batchSize) {
            MaxRangeIterator iterator = new MaxRangeIterator(
                    KeySelector.firstGreaterThan(currentKey),
                    KeySelector.firstGreaterOrEqual(strinc(startKey)),
                    batchSize, new Counters(), db);
            System.out.println(KeySelector.firstGreaterThan(currentKey));
            System.out.println(KeySelector.firstGreaterThan(strinc(startKey)));
            lastFetchSize = 0;
            while (iterator.hasNext() && lastFetchSize < batchSize) {
                KeyValue kv = iterator.next();
                itemsScanned++;
                lastFetchSize++;
                currentKey = kv.getKey();
                int[] offset = new int[1];
                // segment
                String seg = (String) Tuples.getObject(indexTupleLength, kv.getKey(), 0, null, offset);
                if (!seg.equals(lastValue)) {
                    uniqueSegments++;
                }
                lastValue = seg;
                System.out.println(lastValue);
                final String lowercaseSegment = seg.toLowerCase();
                for (int i = 0; i < lowercaseSegment.length() - 2; i++) {
                    trigramsToCleanup.add(lowercaseSegment.substring(i, i + 3));
                }
            }
            System.out.println("Scanned " + itemsScanned + ", last scanned value: " + lastValue);
        }
        System.out.println(trigramsToCleanup);
    }
    private static byte[] makeOpenEndedKey(byte[] key) {
        byte[] newKey = new byte[key.length - 1];
        System.arraycopy(key, 0, newKey, 0, newKey.length);
        return newKey;
    }
}