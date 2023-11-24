package Proposal2;

import com.apple.foundationdb.tuple.ByteArrayUtil;
import com.apple.foundationdb.tuple.Tuple;
import sldb.TupleConstants;
import sldb.ids.IdManager;
import sldb.ids.PerCustomerIdManager;
import sldb.ids.PerCustomerIdManagers;

import java.util.*;

import static SourceCRUD.Executor.db;
import static com.apple.foundationdb.tuple.Tuples.pack;
import static com.wavefront.separators.Separators.splitString;

public class TrigramPart {
    private final String customer;

    public TrigramPart(String customer) {
        this.customer = customer;
    }

    private final static IdManager CUSTOMER_ID_MANAGER = IdManager.createCustomerIdManager(TupleConstants.CUSTOMER_TYPE, db);

    public final static PerCustomerIdManagers ID_MANAGERS =
            new PerCustomerIdManagers(c -> new PerCustomerIdManager(c, CUSTOMER_ID_MANAGER, db, null));

    public Collection<Tuple> createTrigramsForHostname(String hostname) {
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        List<String> parts = splitString(hostname);
        Set<Tuple> trigramParts = new LinkedHashSet<>();
        for (int i = 0; i < parts.size(); i++) {
            String part = parts.get(i);
            for (int j = 0; j < part.length(); j++) {
                if (j + 2 < part.length()) {
                    String trigram = part.substring(j, j + 3);
                    Tuple stiHost = Tuple.from("si").add(customerId).add("trigram").add(trigram).add(part);
                    trigramParts.add(stiHost);
                }
            }
        }
        return trigramParts;
    }

    public Collection<Tuple> createTrigramIndicesForHostname(String hostname) {
        byte[] customerId = ID_MANAGERS.get(customer).getCustomerId();
        List<String> parts = splitString(hostname);
        List<Tuple> trigramIndices = new ArrayList<>();
        Tuple misHost = Tuple.from("si").add(customerId).add("index").add(parts.get(0)).add("");
        trigramIndices.add(misHost);
        for (int i = 1; i < parts.size(); i++) {
            StringBuilder str = new StringBuilder(parts.get(0));
            misHost = Tuple.from("si").add(customerId).add("index").add(parts.get(i)).add(str.toString());
            trigramIndices.add(misHost);
            str.append(parts.get(i));
        }
        return trigramIndices;
    }
}
