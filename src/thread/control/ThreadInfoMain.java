package thread.control;

import static util.MyLogger.log;

public class ThreadInfoMain {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        log("mainThread = " + mainThread.toString());
        log("mainThreadId = " + mainThread.threadId());
        log("mainThread name = " + mainThread.getName());
        log("mainThread prior= " + mainThread.getPriority());
        log("mainThread group= " + mainThread.getThreadGroup());
        log("mainThread state = " + mainThread.getState());
    }
}
