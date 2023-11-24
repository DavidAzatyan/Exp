package Proposal2;

import com.apple.foundationdb.Transaction;
import com.apple.foundationdb.tuple.Tuple;
import wavefront.fdb.DB;
import wavefront.fdb.NamedTxDatabase;

import java.util.Collection;
import java.util.List;

import static com.apple.foundationdb.tuple.Tuples.pack;

public class Executor {
    private static final String storageCluster = "/usr/local/etc/foundationdb/fdb.cluster";
    public static final NamedTxDatabase db = DB.getDatabase(storageCluster);

    public static void main(String[] args) throws InterruptedException {
//        Transaction transaction = db.createTransaction();
        String customer = "Master".toLowerCase();
        List<String> hosts = new ReadSourceID().listHostsStartingAfter("", Integer.MAX_VALUE, true, null, customer);
        for (String host : hosts) {
            System.out.println("hostname : " + host
                    + ",\ntrigrams : " + new TrigramPart(customer).createTrigramsForHostname(host)
                    + ",\nindices : " + new TrigramPart(customer).createTrigramIndicesForHostname(host));
//            if (host.equals("tramp")) {
//                Collection<Tuple> trigramsForHostname = new TrigramPart(customer).createTrigramsForHostname(host);
//                for (Tuple tuple : trigramsForHostname) {
//                    db.run(tr -> {
//                        tr.set(pack(tuple), new byte[]{7,2,0,5,7,5,9,4,0,3,7,9,2,7,9,3,6});
//                        return null;
//                    });
//                }
//                Collection<Tuple> trigramIndicesForHostname = new TrigramPart(customer).createTrigramIndicesForHostname(host);
//                for (Tuple tuple : trigramIndicesForHostname) {
//                    db.run(tr -> {
//                        tr.set(pack(tuple), new byte[]{7,2,0,5,7,5,9,4,0,3,7,9,2,7,9,3,6});
//                        return null;
//                    });
//                }
//                db.close();
//            }
        }
//        System.exit(0);
    }
}
