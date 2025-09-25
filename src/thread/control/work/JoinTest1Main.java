package thread.control.work;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
public class JoinTest1Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyTask(), "t1");
        Thread t2 = new Thread(new MyTask(), "t2");
        Thread t3 = new Thread(new MyTask(), "t3");
        t1.start();
//      join 을 통해 main 스레드가 wating상태로 들어갔다. 그래서 t2 스레드는 t1이 끝난 이후에 실행된다.
        t2.start();
        t3.start();
        t1.join(3000);
        t2.join(3000);
        t3.join(3000);
//      따라서 t1 -> t2 -> t3 처럼 순서대로 스레드가 실행된다.
        System.out.println("모든 스레드 실행 완료");
    }
    static class MyTask implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                log(i);
                sleep(1000);
            }
        }
    }
}
