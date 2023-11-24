package FDB;


import SourceCRUD.ReadDataFromTelemetry;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;

public class SourceMetricsAPI {
    public static void main(String[] args) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .followRedirects(false)
//                .build();
//        Request request = new Request.Builder()
////                .url("http://localhost:8080/chart/metrics/all?h=devsource&q=~agent.buffer")
//                .url("https://workday.wavefront.com/chart/metrics/all?h=s-gqtff33.sys.az1.cust.ash.wd")
//                .method("GET", null)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer 5b345e3e-1938-446a-9509-7b0580e5703a")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
        List<String> hosts = new ReadDataFromTelemetry().listHostsStartingAfter("", Integer.MAX_VALUE, true, "", "master");
        for (String host : hosts) {
            System.out.println(host + " " + new SourceMetricsAPI().GetSourceMetrics(host));
        }
    }

    public String GetSourceMetrics(String source) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(false)
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/chart/metrics/all?h=" + source)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer 51fc8508-e82a-47ea-bce0-e8e0ed845724")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}


