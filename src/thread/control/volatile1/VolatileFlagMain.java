package thread.control.volatile1;

import static util.MyLogger.log;

public class VolatileFlagMain {
    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "work");
        log("runFlag = "+ myTask.runFlag);
        thread.start();
        Thread.sleep(1000);
        myTask.runFlag = false;
        log("runFlag = "+ myTask.runFlag);
        log("main 종료");
    }

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;
        @Override
        public void run() {
            log("task 시작");
            while(runFlag){

            }
            log("task 종료");
        }
    }
}

