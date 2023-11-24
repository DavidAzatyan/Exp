package FDB;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.KeyValue;
import com.apple.foundationdb.Transaction;

public class FDBExample {
    private static final int RANGE_SIZE = 100;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Create a new Database object
        Database db = FDB.selectAPIVersion(630).open();

        byte[] rangeStartKey = {(byte) 0x02, 'h', 'i', (byte) 0x00};
        byte[] rangeEndKey = {(byte) 0x02, 'h', 'i', (byte) 0xff};
        byte[] currentRangeStartKey = rangeStartKey;
        // Create a new transaction object
        Transaction tr = db.createTransaction();

        // Read values from the database
        List<KeyValue> rangeData = tr.getRange(currentRangeStartKey, rangeEndKey, RANGE_SIZE).asList().join();
        for (KeyValue kv : rangeData) {
            String key = new String(kv.getKey(), StandardCharsets.UTF_8);
            String value = new String(kv.getValue(), StandardCharsets.UTF_8);
            System.out.println(key + ": " + value);
        }

        // Commit the transaction
        tr.commit().get();

        // Close the database
        db.close();
    }
}
