package Thread.Synchronized.Example3;

public class MyThread implements Runnable{
    private String threadName;
    private A a;
    MyThread(String threadName, A a){
        this.threadName = threadName;
        this.a = a;
    }
    @Override
    public void run() {
        a.f(threadName);
    }
}
