package Thread.Synchronized.Example3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        Thread thread = new Thread(new MyThread("t1", a));
        MyThread1 thread1 = new MyThread1("t2", a);
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println("Main end");
    }

}
