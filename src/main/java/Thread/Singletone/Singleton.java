package Thread.Singletone;

public class Singleton {
    private static volatile Singleton singleton;

    private Singleton() {

    }

    public Singleton getSingletonInstance() {
        if (singleton == null) {
            synchronized (singleton) {
                if(singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
