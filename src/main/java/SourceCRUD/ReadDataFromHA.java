package SourceCRUD;

import avrobase.Row;
import avrobase.fdb.FDBABAvrobaseSchemas;
import com.apple.foundationdb.KeySelector;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.tuple.Tuple;
import org.apache.commons.compress.utils.Lists;
import wavefront.fdb.Counters;
import wavefront.fdb.MaxRangeIterator;
import wavefront.fdb.NamedTxDatabase;
import wavefront.fdb.util.Collecterator;
import wavefront.machine.TaggedSource;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static SourceCRUD.FDBDeserializerAB.ROW_ID_EXTRACTOR;
import static com.apple.foundationdb.tuple.Tuples.pack;
import static com.google.common.collect.Iterators.limit;
import static sunnylabs.data.DataUtils.makeKeyPrefix;
import static sunnylabs.data.DataUtils.next;

public class ReadDataFromHA {
    Iterator<Row<TaggedSource, byte[]>> readAllTaggedSourcesFromHA(String customer, NamedTxDatabase db) {
        Tuple tableKey = new Tuple().add("customer").add("taggedSources");
        FDBDeserializerAB fdbDeserializerAB = new FDBDeserializerAB(new FDBABAvrobaseSchemas(db));
        KeySelector start = KeySelector.firstGreaterOrEqual(pack(tableKey, makeKeyPrefix(customer)));
        KeySelector guard = KeySelector.firstGreaterOrEqual(pack(tableKey, next(makeKeyPrefix(customer))));

        Iterator<Row<TaggedSource, byte[]>> haIteratorTaggedSource = limit(new Collecterator<>(new MaxRangeIterator(start, guard, Integer.MAX_VALUE, new Counters(), db, false), ROW_ID_EXTRACTOR, (key, collected) -> {
            return fdbDeserializerAB.collectKeyValuesToRow(key.getBytes(), collected);
        }), Integer.MAX_VALUE);

        return haIteratorTaggedSource;
    }

    Iterator<KeyValue> readAllTaggedSourcesKVFromHA(String customer, NamedTxDatabase db) {
        Tuple tableKey = new Tuple().add("customer").add("taggedSources");
        KeySelector start = KeySelector.firstGreaterOrEqual(pack(tableKey, makeKeyPrefix(customer)));
        KeySelector guard = KeySelector.firstGreaterOrEqual(pack(tableKey, next(makeKeyPrefix(customer))));

        Iterator<KeyValue> haIteratorKV = new MaxRangeIterator(start, guard, Integer.MAX_VALUE, new Counters(), db, false);
        int count = 0;
        while (haIteratorKV.hasNext()) {
            KeyValue kv = haIteratorKV.next();
//            Tuple keyT = Tuple.fromBytes(kv.getKey());
//            List<Object> items = keyT.getItems();
            // normal key.
//            System.out.println(new BytesKey((byte[]) items.get(items.size() - 1)));
            System.out.println(kv);
            count++;
        }
        System.out.println("Count of sources from HA : " + count);
        return haIteratorKV;
    }
}
