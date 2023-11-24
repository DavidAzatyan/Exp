package Thread.Synchronized.Example1;

public class Counter {
    private boolean isTrue = true;

    public synchronized void count(String name) throws InterruptedException {

        System.out.println("starting");
        for (int i = 0; i < 11; i++) {
            System.out.println(name + ":" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
