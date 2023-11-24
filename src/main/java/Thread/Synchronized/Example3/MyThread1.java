package Thread.Synchronized.Example3;

public class MyThread1 extends Thread {
    private String threadName;
    private A a;
    MyThread1(String threadName, A a){
        this.threadName = threadName;
        this.a = a;
    }
    @Override
    public void run(){
        a.g(threadName);
    }
}
