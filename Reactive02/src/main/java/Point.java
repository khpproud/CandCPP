import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 여러 필드를 한 번의 처리로 변경해야 할 때 이를 처리하는 동안 다른 스레드가 해당 객체에 접근
 */
public class Point {
    private final AtomicInteger x = new AtomicInteger(0);
    private final AtomicInteger y = new AtomicInteger(0);

    void rightUp() {
        x.incrementAndGet();
        y.incrementAndGet();
    }

    int getX() {
        return x.get();
    }

    int getY() {
        return y.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Point counter = new Point();

        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                counter.rightUp();
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Boolean> future1 = executorService.submit(task, true);
        Future<Boolean> future2 = executorService.submit(task, true);

        if (future1.get() && future2.get())
            System.out.println(counter.getX() + " : " + counter.getY());
        else
            System.out.println("실패");

        executorService.shutdown();
    }
}
