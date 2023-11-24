package Thread.Volatile;

public class TestVolatile extends Thread {
    boolean keepingRun = true;

    @Override
    public void run() {
        long count = 0;
        while (keepingRun) {
            count++;
        }
        System.out.println("prcav");
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile testVolatile = new TestVolatile();
        testVolatile.start();
        Thread.sleep(1000);
        testVolatile.keepingRun = false;
        System.out.println("keep Running set to false");
    }
}
