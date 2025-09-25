package thread.start.work;

import static util.MyLogger.log;

public class MultiThreadMain {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(()->{
            while(true){
                log("A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread thread2 = new Thread(()->{
            while(true){
                log("B");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
