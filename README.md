# 자바 동시성 프로그래밍 학습 프로젝트

이 프로젝트는 자바의 동시성(Concurrency) 프로그래밍 개념을 학습하고 실습한 내용을 담고 있습니다.

## 🎯 학습한 주요 개념들

### 1. 스레드 기초 (`thread/start/`)

#### Thread vs Runnable
- **Thread 클래스 상속**: `HelloThread.java:3`
  - Thread 클래스를 직접 상속받아 구현
  - `run()` 메서드 오버라이드

- **Runnable 인터페이스 구현**: `HelloRunnable.java`
  - 더 유연한 방식 (다중 상속 문제 해결)
  - 함수형 인터페이스로 람다 표현식 사용 가능

#### 스레드 생성과 실행
- `start()` vs `run()` 메서드의 차이점
- 다중 스레드 생성 및 관리

### 2. 메모리 가시성 문제 (`thread/control/volatile1/`)

#### volatile 키워드: `VolatileFlagMain.java:19`
```java
volatile boolean runFlag = true;
```
- CPU 캐시와 메인 메모리 간의 가시성 문제 해결
- 스레드 간 변수 값 동기화 보장
- 원자성은 보장하지 않음 (단순 읽기/쓰기만)

### 3. 스레드 제어

#### Join 메서드 (`thread/control/join/`)
- 다른 스레드의 완료를 기다리는 메커니즘
- 스레드 실행 순서 제어

#### 인터럽트 (`thread/control/interrupt/`)
- `MyPrinterV1.java:29`: volatile 플래그를 사용한 스레드 종료
- 스레드 간 안전한 통신 방식
- `ConcurrentLinkedQueue` 사용으로 스레드 안전성 보장

### 4. 동기화 메커니즘 (`thread/control/sync/`)

#### 동시성 문제 사례: 은행 계좌 출금 시나리오
여러 스레드가 동시에 같은 계좌에서 출금할 때 발생하는 경쟁 상태(Race Condition) 문제를 해결하는 다양한 방법들:

#### BankAccountV1: 동기화 없음 (`BankAccountV1.java:14`)
```java
public boolean withdraw(int amount) {
    // 동기화 없음 - 경쟁 상태 발생 가능
}
```
- 동시 접근 시 데이터 무결성 문제 발생

#### BankAccountV2: synchronized 메서드 (`BankAccountV2.java:14`)
```java
public synchronized boolean withdraw(int amount) {
    // 메서드 전체 동기화
}
```
- 메서드 레벨 동기화
- 내재적 락(intrinsic lock) 사용

#### BankAccountV3: synchronized 블록 (`BankAccountV3.java:17`)
```java
synchronized (this) {
    // 필요한 부분만 동기화
}
```
- 블록 레벨 동기화로 성능 최적화
- 더 세밀한 동기화 제어

#### BankAccountV4: ReentrantLock (`BankAccountV4.java:12`)
```java
private final Lock lock = new ReentrantLock();

public boolean withdraw(int amount) {
    lock.lock();
    try {
        // 임계 영역
    } finally {
        lock.unlock();
    }
}
```
- 명시적 락 사용
- 더 유연한 락 제어 (타임아웃, 인터럽트 가능)
- **주의**: 현재 코드에서 finally 블록 누락 - 개선 필요

### 5. LockSupport (`thread/control/sync/lock/`)

#### 저수준 스레드 제어: `LockSupportMainV1.java:30`
```java
LockSupport.park();    // 스레드 일시 정지
LockSupport.unpark(thread);  // 스레드 깨우기
```
- `wait()`/`notify()`보다 더 유연한 스레드 제어
- 인터럽트에 응답 가능

### 6. 생산자-소비자 패턴 (`thread/bounded/`)

#### Bounded Queue 구현
여러 버전의 경계가 있는 큐(Bounded Queue) 구현을 통한 생산자-소비자 패턴 학습:

#### BoundedQueueV1: 단순 동기화 (`BoundedQueueV1.java:18`)
```java
@Override
public synchronized void put(String data) {
    if (queue.size() == max) {
        log("[put] 큐가 가득 참, 버린다.: "+ data);
        return;
    }
    queue.offer(data);
}
```
- 큐가 가득 찬 경우 데이터를 버리는 방식
- 간단하지만 데이터 손실 발생

#### BoundedQueueV2-V6: 대기 메커니즘
- **V2**: `wait()`/`notify()` 사용한 블로킹 방식
- **V3**: `notifyAll()` 사용으로 모든 대기 스레드 깨우기
- **V4**: `InterruptedException` 처리 개선
- **V5**: `Lock`과 `Condition` 사용
- **V6**: `BlockingQueue` 인터페이스 활용

### 7. CAS와 원자적 연산 (`thread/cas/`)

#### Compare-And-Swap 연산
락 없는 동기화를 위한 CAS(Compare-And-Swap) 연산 학습:

#### 다양한 구현 방식 비교
- **BasicInteger**: 동기화 없음 - 경쟁 상태 발생
- **VolatileInteger**: volatile 키워드 사용 - 가시성 보장, 원자성 미보장
- **SyncInteger**: synchronized 사용 - 완전한 동기화
- **MyAtomicInteger**: `AtomicInteger` 사용 (`MyAtomicInteger.java:7`)

```java
private AtomicInteger value = new AtomicInteger(0);

@Override
public void increment() {
    value.incrementAndGet();  // CAS 기반 원자적 연산
}
```

#### CAS의 장점
- 락 없는(lock-free) 동기화
- 높은 성능과 확장성
- 데드락 방지

### 8. 스레드 안전한 컬렉션 (`thread/collection/`)

#### 리스트 구현체 비교 학습

#### BasicList vs SyncList
- **BasicList**: 스레드 안전하지 않음
- **SyncList**: `synchronized` 키워드로 동기화 (`SyncList.java:21`)

```java
@Override
public synchronized void add(Object o) {
    elementData[size] = o;
    Thread.sleep(100);  // 동시성 문제 시뮬레이션
    size++;
}
```

#### SyncProxyList: 데코레이터 패턴
기존 리스트를 감싸서 동기화 기능 추가:
- 기존 코드 수정 없이 동기화 기능 추가
- 프록시 패턴을 통한 투명한 동기화

### 9. 스레드 안전한 자료구조

#### ConcurrentLinkedQueue: `MyPrinterV1.java:30`
```java
Queue<String> jobQueue = new ConcurrentLinkedQueue<>();
```
- 락 없는(lock-free) 스레드 안전 큐
- 프로듀서-컨슈머 패턴 구현

