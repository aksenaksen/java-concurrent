package thread.cas;

import java.util.ArrayList;
import java.util.List;

public class IncrementThreadMain {

    public static final int THREAD_CNT = 1000;

    public static void main(String[] args) throws InterruptedException {
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                incrementInteger.increment();
            }
        };

        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i< THREAD_CNT; i++){
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for(Thread thread : threads){
            thread.join();
        }

        int result = incrementInteger.get();

        System.out.println(incrementInteger.getClass().getSimpleName() + " : "+result);
    }
}
