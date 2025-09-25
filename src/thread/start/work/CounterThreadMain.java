package thread.start.work;

public class CounterThreadMain {
    public static void main(String[] args) {
        Thread thread = new CounterThread();
        thread.start();

    }
}
