package Thread.Synchronized.Example1;

import Thread.Synchronized.Example1.Counter;

public class MyThread implements Runnable{

    private String name;
    private Counter counter;

    public MyThread(String name, Counter counter){
        this.name = name;
        this.counter = counter;
    }
    @Override
    public void run() {
        try {
            counter.count(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
