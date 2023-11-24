package Thread;

public class Test {

    static int[] arr = {1,5,7,9,0,6,4,-8,59};

    static int min = arr[0];
    static int max = arr[0];

    public static void main(String[] args) throws InterruptedException {
        Thread minThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (min > arr[i]) {
                        min = arr[i];
                    }
                }
            }
        });
        Thread maxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (max < arr[i]) {
                        max = arr[i];
                    }
                }
            }
        });
        minThread.start();
        maxThread.start();
        while (maxThread.isAlive() || minThread.isAlive()){

        }
        System.out.println(min);
        System.out.println(max);
        System.out.println("sdfds");
    }
    static void f1(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        System.out.println(min);
        System.out.println(max);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    static void f(){
        long start1 = System.currentTimeMillis();
        Thread minThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (min > arr[i]) {
                        min = arr[i];
                    }
                }
            }
        });
        Thread maxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < arr.length; i++) {
                    if (max < arr[i]) {
                        max = arr[i];
                    }
                }
            }
        });
        minThread.start();
        maxThread.start();
        long end1 = System.currentTimeMillis();
        System.out.println(min);
        System.out.println(max);
        System.out.println(end1 - start1);
    }

}
