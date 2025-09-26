package thread.cas;

public class IncrementPerformanceMain {
    private static final long COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger){
        long start = System.currentTimeMillis();
        for(long i =0; i < COUNT; i++){
            incrementInteger.increment();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
