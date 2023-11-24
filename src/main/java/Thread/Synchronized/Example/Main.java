package Thread.Synchronized.Example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.transfer(500);
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.transfer(500);
            }
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(bank.getAmount());
    }
}
