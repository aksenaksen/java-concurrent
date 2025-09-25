package thread.control.interrupt.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

public class MyPrinterV1 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");

        printerThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요 :");
            String input = scanner.nextLine();
            if(input.equals("end")){
                printer.work = false;
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while(work) {
                if( jobQueue.isEmpty() ) {
                    continue;
                }

                String job = jobQueue.poll();
                log("출력 시작: "+ job + ", 대기문서"+ jobQueue);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log("출력 완료");
            }
            log("프린터 종료");
        }


        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
