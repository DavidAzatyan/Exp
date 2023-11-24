package FDB;

import com.apple.foundationdb.*;
import com.apple.foundationdb.tuple.Tuple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ReadFromHAFDBTaggedSources {
    private static final int API_VERSION = 610;
    private static final int RANGE_SIZE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        Database db = FDB.selectAPIVersion(API_VERSION).open();

        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter("TaggedSources_local_data.txt"))) {
            System.out.println("Output file opened");

//            byte[] rangeStartKey = {(byte) 0x02, 'c', 'u', 's', 't', 'o', 'm', 'e', 'r', (byte) 0x00, (byte) 0x02
//                    , 't', 'a', 'g', 'g', 'e', 'd', 'S', 'o', 'u', 'r', 'c', 'e', 's', (byte) 0x00};
//            byte[] rangeEndKey = {(byte) 0x02, 'c', 'u', 's', 't', 'o', 'm', 'e', 'r', (byte) 0x00, (byte) 0x02
//                    , 't', 'a', 'g', 'g', 'e', 'd', 'S', 'o', 'u', 'r', 'c', 'e', 's', (byte) 0x00,(byte) 0xff};
//            byte[] rangeStartKey = {(byte) 0x02, 'c', 'u', 's', 't', 'o', 'm', 'e', 'r', (byte) 0x00, (byte) 0x02
//                    , 't', 'a', 'g', 'g', 'e', 'd', 'S', 'o', 'u', 'r', 'c', 'e', 's', (byte) 0x00, (byte) 0x01, 'm', 'a', 's', 't', 'e', 'r'};
//            byte[] rangeEndKey = {(byte) 0x02, 'c', 'u', 's', 't', 'o', 'm', 'e', 'r', (byte) 0x00, (byte) 0x02
//                    , 't', 'a', 'g', 'g', 'e', 'd', 'S', 'o', 'u', 'r', 'c', 'e', 's', (byte) 0x00, (byte) 0x01, 'm', 'a', 's', 't', 'e', 'r', (byte) 0xff};
            byte[] rangeStartKey = {(byte) 0x02, 'u', (byte) 0x00, (byte) 0x02, 'v', (byte) 0x00, (byte) 0x01,  (byte) 0x01, (byte) 0x00};
            byte[] rangeEndKey = {(byte) 0x02, 'u', (byte) 0x00, (byte) 0x02, 'v', (byte) 0x00, (byte) 0x01, (byte) 0x01,  (byte) 0x01, (byte) 0x00, (byte) 0xff};
            byte[] currentRangeStartKey = rangeStartKey;
            while (compare(currentRangeStartKey, rangeEndKey) < 0) {
                Transaction tr = db.createTransaction();
                List<KeyValue> rangeData = tr.getRange(currentRangeStartKey, rangeEndKey, RANGE_SIZE, true).asList().join();
                Set<KeyValue> treeSet = new HashSet<>(rangeData);
                rangeData = new ArrayList<>(treeSet);
                for (int i = 0; i < rangeData.size(); i++) {
                    KeyValue keyValue = rangeData.get(i);
                    byte[] key = keyValue.getKey();
                    byte[] value = keyValue.getValue();
                    try {
                        Tuple tupleKey = Tuple.fromBytes(key);
                        String keyStr = tupleKey.toString();
                        String valueStr = Tuple.fromBytes(value).toString();
                        String line = i + " " + keyStr + " : " + valueStr + "\n";
//                        if (keyStr.contains("b\"master\\")) {
//                            String[] split = keyStr.split(",");
//                            String source = split[split.length - 1].substring(13, split[split.length - 1].length() - 2);
//                            String metrics = new SourceMetricsAPI().GetSourceMetrics(source);
//                            String line = i + " " + "\"source\": " + source + " : " + metrics + "\n";
                        outputFile.write(line);
//                        }
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
