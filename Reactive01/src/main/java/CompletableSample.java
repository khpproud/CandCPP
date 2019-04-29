import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// Completable - 데이터를 통지하지 않고 완료를 통지하거나 에러를 통지
public class CompletableSample {
    public static void main(String[] args) throws InterruptedException {
        // Completable을 생성
        Completable completable = Completable.create(emitter -> {
            // 업무 로직 처리 ~
            Thread.sleep(100L);
            // 완료 통지
            emitter.onComplete();
        });

        completable
                // Completable을 비동기로 실행
                .subscribeOn(Schedulers.computation())
                // 구독
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 아무것도 하지 않음
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        // 잠시 기다림
        Thread.sleep(200L);
    }
}
