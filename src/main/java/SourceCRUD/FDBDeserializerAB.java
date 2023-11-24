package SourceCRUD;

import avrobase.AvroBaseException;
import avrobase.Row;
import avrobase.fdb.FDBABAvrobaseSchemas;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.tuple.ByteArrayUtil;
import com.apple.foundationdb.tuple.Tuple;
import com.apple.foundationdb.tuple.Tuples;
import com.wavefront.common.BytesKey;
import com.wavefront.common.Compressor;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import wavefront.machine.TaggedSource;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static com.apple.foundationdb.tuple.ByteArrayUtil.printable;

public class FDBDeserializerAB  {

    private final DecoderFactory decoderFactory;
    private final FDBABAvrobaseSchemas schemas;
    private final Schema readSchema;

    FDBDeserializerAB(FDBABAvrobaseSchemas schemas) {
        this.decoderFactory = new DecoderFactory();
        this.schemas = schemas;
        try {
            this.readSchema = (Schema) TaggedSource.class.getField("SCHEMA$").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException("Tried accessing TaggedSource's SCHEMA$ field", e);
        }
    }

    protected Row<TaggedSource, byte[]> collectKeyValuesToRow(byte[] rowKey, Iterable<KeyValue> keyValues) {
        return collectKeyValuesToRow(rowKey, keyValues, null);
    }

    private Row<TaggedSource, byte[]> collectKeyValuesToRow(byte[] rowKey, Iterable<KeyValue> keyValues,
                                                            @Nullable AtomicBoolean schemaChanged) {
        @Nullable
        byte[] bytes = collectValues(keyValues);
        if (bytes == null) {
            return null;
        }
        try {
            return decodeRow(rowKey, bytes, schemaChanged);
        } catch (RuntimeException ex) {
            throw new AvroBaseException("Cannot decode row: " + printable(rowKey), ex);
        }
    }

    private byte[] collectValues(Iterable<KeyValue> keyValues) {
        int length = 0;
        for (KeyValue kv : keyValues) {
            length += kv.getValue().length;
        }
        if (length > 0) {
            byte[] result = new byte[length];
            int pos = 0;
            for (KeyValue kv : keyValues) {
                final byte[] value = kv.getValue();
                System.arraycopy(value, 0, result, pos, value.length);
                pos += value.length;
            }
            return result;
        }
        return null;
    }

    Row<TaggedSource, byte[]> decodeRow(byte[] row, byte[] bytes, @Nullable AtomicBoolean schemaChanged) {
        int[] offset = new int[1];
        final long schemaId = Tuples.getLong(0, bytes, 0, null, offset);
        Schema writeSchema = schemaForId(schemaId);
        if (schemaChanged != null)
            schemaChanged.set(!writeSchema.equals(readSchema));

        SpecificDatumReader<TaggedSource> reader = new SpecificDatumReader<>(writeSchema, readSchema);
        long version = Tuples.getLong(offset[0], bytes, 0, null, offset);
        byte[] valueBytes = Tuples.getBytes(offset[0], bytes, 0, null, offset);
        // do a check to see if it's compressed first (this is not definitive).
        if (Compressor.isCompressed(valueBytes)) {
            try {
                // decompress first.
                BinaryDecoder bd = decoderFactory.binaryDecoder(Compressor.decompress(valueBytes), null);
                return getRowAsync(row, reader, version, bd);
            } catch (IOException ex) {
                // as-is then.
                BinaryDecoder bd = decoderFactory.binaryDecoder(valueBytes, null);
                try {
                    return getRowAsync(row, reader, version, bd);
                } catch (IOException e) {
                    throw new AvroBaseException("Cannot decode valueBytes for row: " + ByteArrayUtil.printable(row) + ", " +
                            "attempted to decode after gzip decompression and as-is", e);
                }
            }
        } else {
            // direct first.
            try {
                BinaryDecoder bd = decoderFactory.binaryDecoder(valueBytes, null);
                return getRowAsync(row, reader, version, bd);
            } catch (IOException ex) {
                // try decompression then.
                BinaryDecoder bd = decoderFactory.binaryDecoder(Compressor.decompress(valueBytes), null);
                try {
                    return getRowAsync(row, reader, version, bd);
                } catch (IOException e) {
                    throw new AvroBaseException("Cannot decode valueBytes for row: " + ByteArrayUtil.printable(row) + ", " +
                            "attempted to decode as-is and after gzip decompression", e);
                }
            }
        }
    }

    private Schema schemaForId(final long schemaId) {
        return schemas.getSchemaById(schemaId);
    }

    private Row<TaggedSource, byte[]> getRowAsync(byte[] row, SpecificDatumReader<TaggedSource> reader, long version, BinaryDecoder bd)
            throws IOException {
        TaggedSource read = reader.read(null, bd);
        return new Row<>(read, row, version);
    }

    public final static Function<KeyValue, BytesKey> ROW_ID_EXTRACTOR = keyValue -> {
        // extract the row key from the KV.
        Tuple keyT = Tuple.fromBytes(keyValue.getKey());
        List<Object> items = keyT.getItems();
        if (items.get(items.size() - 1) instanceof Long) {
            // this is a chunked key.
            return new BytesKey((byte[]) items.get(items.size() - 2));
        } else if (items.get(items.size() - 1) instanceof byte[]) {
            // normal key.
            return new BytesKey((byte[]) items.get(items.size() - 1));
        } else {
            throw new RuntimeException("Cannot decode row: " + ByteArrayUtil.printable(keyValue.getKey()));
        }
    };
}
