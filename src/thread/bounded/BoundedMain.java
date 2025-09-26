package thread.bounded;

import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log;

public class BoundedMain {
    public static void main(String[] args) {

        BoundedQueue queue = new BoundedQueueV6_1(2);

        producerFirst(queue);
//        consumerFirst(queue);

    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }


    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for(int i=1; i<=3; i++){
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer-" + i);
            threads.add(consumer);
            consumer.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("현재상태 출력, 큐 데이터: " + queue);
        threads.forEach(
                thread -> {
                    log(thread.getName() + ": " + thread.getState());
                }
        );
    }
    
    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for(int i=1; i<=3; i++){
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
