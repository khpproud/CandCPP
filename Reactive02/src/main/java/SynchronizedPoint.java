import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * synchronized 블록을 사용한 Point 클래스
 */
public class SynchronizedPoint {
    private final Object lock = new Object();

    private int x;
    private int y;

    void rightUp() {
        synchronized (lock) {
            x++;
            y++;
        }
    }

    int getX() {
        synchronized (lock) {
            return x;
        }
    }

    int getY() {
        synchronized (lock) {
            return y;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final SynchronizedPoint counter = new SynchronizedPoint();

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
