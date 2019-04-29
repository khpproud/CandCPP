import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicCounter를 사용한 예제
 */
public class AtomicCounterSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 순차로 값을 증가시키는 AtomicCount 객체
        final AtomicCounter counter = new AtomicCounter();

        // 10,000번 계산하는 비동기 작업 처리
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        };

        // 비동기 처리 작업 생성 준비
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 새로운 스레드로 시작
        Future<Boolean> future1 = executorService.submit(task, true);
        Future<Boolean> future2 = executorService.submit(task, true);

        // 결과가 반환될 때까지 기다림
        if (future1.get() && future2.get()) {
            // 결과를 출력
            System.out.println(counter.get());
        } else {
            System.out.println("실패");
        }

        // 비동기 처리 작업을 종료
        executorService.shutdown();
    }

    private static class AtomicCounter {
        private final AtomicInteger count = new AtomicInteger(0);

        void increment() {
            count.incrementAndGet();
        }

        int get() {
            return count.get();
        }
    }
}
