package Thread.Synchronized.Example5;

public class Counter {
    boolean isFirst = true;
    boolean isSecond = false;
    boolean isThird = false;
    //TODO don`t understand completely
    public synchronized void count(String threadName) {
        for (int i = 0; i < 11; i++) {
            if(i == 6 && isFirst && !isSecond){
                isFirst = false;
                isSecond = true;
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i == 7 && isSecond && !isFirst){
                try {
                    isFirst = false;
                    isSecond = false;
                    isThird = true;
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i == 10 && !isFirst && !isSecond && isThird){
                isThird = false;
                //notify()
                notifyAll();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + ":" + i);
        }
    }
}
