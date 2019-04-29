import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 두 개의 스레드로 접근해 처리하는 예제 - 원자성이 깨지는 경우
 */
public class CounterSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 순차적으로 값을 증가시키는 Count 객체
        final Counter counter = new Counter();

        // 10,000번 계산하는 비동기 작업 처리
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        };

        // 비동기 처리 작업 생성을 준비
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 새로운 스레드로 시작
        Future<Boolean> future1 = executorService.submit(task, true);
        Future<Boolean> future2 = executorService.submit(task, true);

        // 결과가 반활될 때까지 기다림
        if (future1.get() && future2.get()) {
            // 계산 결과 출력 - 실행할 때마다 결과가 달라짐
            System.out.println(counter.get());
        } else {
            System.out.println("실패");
        }

        // 비동기 처리 작업 종료
        executorService.shutdown();
    }
}

/** 순차로 값을 증가시키는 클래스 */
class Counter {
    // volatile -> 업데이트한 값은 메모리에 반드시 반영됨
    private volatile int count;

    void increment() {
        count++;
    }

    int get() {
        return count;
    }
}