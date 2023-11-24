package Thread.Synchronized.Example4;

public class Counter {
    boolean isFirst = true;
    public synchronized void count(String threadName) {
        for (int i = 0; i < 11; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + ":" + i);
            if(i == 5 && isFirst){
                try {
                    isFirst = false;
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i == 10){
                notify();
            }
        }
    }
}
