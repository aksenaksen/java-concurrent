package thread.control.volatile1;

import static util.MyLogger.log;

public class VolatileCountMain {
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread thread = new Thread(task,"work");
        thread.start();

        Thread.sleep(1000);
        task.flag = false;
        log("flag = " + task.flag + "count = " + task.count + "in main()");

    }

    static class MyTask implements Runnable {

        volatile boolean flag = true;
        volatile long count;

        @Override
        public void run() {
            while(flag){
                count++;
                if(count % 100_000_000 == 0){
                    log("flag = " + flag + "count = " + count + "in while()");
                }
            }
            log("flag = " + flag + "count = "+ count + "종료");

        }
    }
}
