package SourceCRUD;

import avrobase.Row;
import sldb.ids.IdTrigramIndices;
import sldb.ids.PerCustomerIdTrigramIndices;
import wavefront.fdb.DB;
import wavefront.fdb.NamedTxDatabase;
import wavefront.machine.TaggedSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Executor {
    private static final String storageCluster = "/usr/local/etc/foundationdb/fdb.cluster";
    public static final NamedTxDatabase db = DB.getDatabase(storageCluster);

    public static void main(String[] args) {
        String customer = "Master".toLowerCase();
//        Iterator<Row<TaggedSource, byte[]>> rowIterator = new ReadDataFromHA().readAllTaggedSourcesFromHA(customer, db);
//        while (rowIterator.hasNext()) {
//            Row<TaggedSource, byte[]> next = rowIterator.next();
//            TaggedSource value = next.value;
//            System.out.println("Tagged source : " + value);
//            System.out.println(new ReadDataFromTelemetry().checkHostIdExisting(customer, value.getHostname()));
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println("_______________________________________________________________________________________________");
//        System.out.println();


        List<String> hostnames = new ReadDataFromTelemetry().listHostsStartingAfter("", Integer.MAX_VALUE, true, "",customer);
        System.out.println("Count of sources from Telemetry data : " + hostnames.size());
        System.out.println(hostnames);
        for (String hostname : hostnames) {
            System.out.println();
            System.out.println("Source : " + hostname);
            new ReadDataFromTelemetry().latestTimeStampForSourceFromHMT(customer, hostname);
            System.out.println();
        }
        System.exit(0);
    }

}
