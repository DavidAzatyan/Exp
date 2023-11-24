package FDB.HostIndices;

import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.Transaction;
import com.apple.foundationdb.tuple.Tuple;
import com.google.common.primitives.Longs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReadFromFDBTrigramAndIndex {

    private static final int API_VERSION = 610;
    private static final int RANGE_SIZE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        Database db = FDB.selectAPIVersion(API_VERSION).open();

        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter("TRIGRAM_INDEX_local_data.txt"))) {
            System.out.println("Output file opened");

            byte[] rangeStartKey = {(byte) 0x02, 'h', 'i', (byte) 0x00, (byte) 0x01,(byte)0x01,(byte)0x00};
            byte[] rangeEndKey = {(byte) 0x02, 'h', 'i', (byte) 0x00, (byte) 0x01,(byte)0x01,(byte)0x00, (byte) 0xff};

//            byte[] rangeStartKey = {(byte) 0x02, 's', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 't', 'r', 'i', 'g', 'r', 'a', 'm', (byte) 0x00};
//            byte[] rangeEndKey = {(byte) 0x02, 's', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 't', 'r', 'i', 'g', 'r', 'a', 'm', (byte) 0x00, (byte) 0xff};

//            byte[] rangeStartKey = {(byte) 0x02, 's', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 'm', 't', (byte) 0x00};
//            byte[] rangeEndKey = {(byte) 0x02, 's', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 'm', 't', (byte) 0x00, (byte) 0xff};

//            byte[] rangeStartKey = {(byte) 0x02, 'h', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 'i', 'n', 'd', 'e', 'x', (byte) 0x00};
//            byte[] rangeEndKey = {(byte) 0x02, 'h', 'i', (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x02, 'i', 'n', 'd', 'e', 'x', (byte) 0x00, (byte) 0xff};

            byte[] currentRangeStartKey = rangeStartKey;

            while (compare(currentRangeStartKey, rangeEndKey) < 0) {
                Transaction tr = db.createTransaction();
                List<KeyValue> rangeData = tr.getRange(currentRangeStartKey, rangeEndKey, RANGE_SIZE).asList().join();

                for (int i = 0; i < rangeData.size(); i++) {
                    KeyValue keyValue = rangeData.get(i);
                    byte[] key = keyValue.getKey();
                    byte[] value = keyValue.getValue();
                    try {
                        String keyStr = Tuple.fromBytes(key).toString();
//                        String valueStr = Tuple.fromBytes(value).toString();
                        long valueDecode = Longs.fromByteArray(keyValue.getValue());
                        String line = i + " " + keyStr + " : " + valueDecode + "\n";
                        outputFile.write(line);
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                }

                if (rangeData.size() < RANGE_SIZE) {
                    tr.close();
                    System.out.println("Close transaction");
                    break;
                }

                currentRangeStartKey = rangeData.get(rangeData.size() - 1).getKey();
                tr.close();
            }

            System.out.println("The End");
        }
    }

    private static int compare(byte[] key1, byte[] key2) {
        for (int i = 0; i < Math.min(key1.length, key2.length); i++) {
            int diff = Integer.compareUnsigned(key1[i], key2[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return Integer.compare(key1.length, key2.length);
    }
}
