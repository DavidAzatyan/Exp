package Thread.ThreadPool.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        System.out.println(ForkJoinAdd.startForkJoinSum(1_000_000));
        long e = System.currentTimeMillis();
        System.out.println(e - s);

        long s1 = System.currentTimeMillis();
        System.out.println(sum(1_000_000));
        long e1 = System.currentTimeMillis();
        System.out.println(e1 - s1);

        long i = 500000500000L;
    }
    static long sum(int num){
        long result = 0;
        for (int i = 0; i <= num; i++) {
            result += i;
        }
        return result;
    }
}
