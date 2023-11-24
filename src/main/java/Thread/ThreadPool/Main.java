package Thread.ThreadPool;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static Runnable r1 = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("barevik");
        }
    };
    static Runnable r2 = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("barev");
        }
    };

    static Callable<String> callable1 = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "java";
        }
    };

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(r1);
        service.submit(r2);
        Future<String> submit = service.submit(callable1);
        System.out.println(submit.get());
        service.shutdown();
        System.out.println("dskng");
        AtomicInteger integer = new AtomicInteger(1);
    }
}
