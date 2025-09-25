package thread.control.interrupt.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

public class MyPrinterV2 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");

        printerThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요 :");
            String input = scanner.nextLine();
            if(input.equals("end")){
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while(!Thread.interrupted()) {
                if( jobQueue.isEmpty() ) {
                    Thread.yield();
                    continue;
                }
                try {
                    String job = jobQueue.poll();
                    log("출력 시작: "+ job + ", 대기문서"+ jobQueue);
                    Thread.sleep(3000);
                    log("출력 완료");
                } catch (InterruptedException e) {
                    log("인터럽트!");
                    break;
                }
            }
            log("프린터 종료");
        }


        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
