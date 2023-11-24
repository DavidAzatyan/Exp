package Thread.Synchronized.Example5;


public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread = new Thread(new MyThread("t1", counter));
        Thread thread1 = new Thread(new MyThread("t2", counter));
        Thread thread2 = new Thread(new MyThread("t3", counter));
        thread.start();
        thread1.start();
        thread2.start();
    }
}
