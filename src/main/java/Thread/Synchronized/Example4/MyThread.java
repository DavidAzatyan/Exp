package Thread.Synchronized.Example4;

public class MyThread implements Runnable{
    private String threadName;
    private Counter counter;
    MyThread(String threadName, Counter counter){
        this.threadName = threadName;
        this.counter = counter;
    }
    @Override
    public void run() {
            counter.count(threadName);

    }
}
