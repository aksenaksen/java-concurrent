package thread.control.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;

public class BankAccountV4 implements BankAccount {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV4(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작:" + getClass().getSimpleName());
        lock.lock();
        log("[검증시작] 출금액 " + amount + " , 잔액" + balance);
        if (balance < amount) {
            log("[검증 실패] 출금액 " + amount + " , 잔액" + balance);
            return false;
        }

        log("[검증완료] 출금액 " + amount + " , 잔액" + balance);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        balance = balance - amount;
        log("[출금 완료] 출금액 " + amount + " , 잔액" + balance);
        lock.unlock();
        log("거래 종료");

        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
