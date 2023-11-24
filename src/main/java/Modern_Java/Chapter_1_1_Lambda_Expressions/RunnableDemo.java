package Modern_Java.Chapter_1_1_Lambda_Expressions;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.*;

public class RunnableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Anonymous Inner Class
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Anonymous Inner Class");
            }
        }).start();

        //Lambda expression
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Lambda expression");
        }).start();


        //Lambda for run method and Lambda for call method
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runnable");
        });
        Future<String> future = service.submit(() -> "Callable");
        System.out.println(future.get());
        service.shutdown();

        //Lambda expression with a few arguments

        File directory = new File("./src/main/java/Algorithms");

        String[] names = directory.list((File dir, String name) -> name.endsWith(".java"));
        System.out.println(Arrays.asList(names));

        System.out.println("Main thread");

        Flyable flyable = new Flyable() {
            @Override
            public void fly() {
                long start = System.currentTimeMillis();
                long end = System.currentTimeMillis();
                System.out.println(end - start);
            }
        };

        flyable.fly();

        Flyable flyable1 = () -> {
            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        };

        flyable1.fly();
    }
}
