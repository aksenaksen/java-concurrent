package thread.control.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;

public class LockSupportMainV1 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(),"parkTest1");
        thread1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log("Thread-1 state : " + thread1.getState());
        log("main -> unpark");
//        LockSupport.unpark(thread1);

        thread1.interrupt();
    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료 , state: "+ Thread.currentThread().getState());
            log("인터럽트 상태 : "+ Thread.currentThread().isInterrupted());

        }
    }
}
