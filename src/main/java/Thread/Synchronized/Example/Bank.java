package Thread.Synchronized.Example;

public class Bank {
    private Integer amount = 1000;

    public synchronized void transfer(int money) {
        int newBalance = amount - money;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        amount = newBalance;
    }

    public int getAmount() {
        return amount;
    }
}
