package Thread.Synchronized.Example3;

public class A {
    public synchronized void f(String name) {
        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ":" + i);
        }
    }

    public synchronized void g(String name) {
        for (int i = 0; i <= 10 ; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ":" + i);
        }
    }
}
