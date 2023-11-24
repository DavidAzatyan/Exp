package Thread.Synchronized.Example4;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread = new Thread(new MyThread("t1", counter));
        Thread thread1 = new Thread(new MyThread("t2", counter));
        thread.start();
        thread1.start();
    }
}
