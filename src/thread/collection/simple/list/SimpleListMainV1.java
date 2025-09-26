package thread.collection.simple.list;

import static util.MyLogger.log;

public class SimpleListMainV1 {

    public static void main(String[] args) throws InterruptedException {

//        test(new SyncList());

        test(new SyncProxyList(new BasicList()));
    }

    public static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        Runnable addA = new Runnable() {

            @Override
            public void run() {
                list.add("A");
                log("Thread-1: list.add(A)");
            }
        };

        Runnable addB = new Runnable() {

            @Override
            public void run() {
                list.add("B");
                log("Thread-2: list.add(B)");
            }
        };

        Thread threadA = new Thread(addA, "Thread 1");
        Thread threadB = new Thread(addB, "Thread-2");
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        log(list);
    }
}
