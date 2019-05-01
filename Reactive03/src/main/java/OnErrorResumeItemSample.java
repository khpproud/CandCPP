import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 에러가 발생하면 대체 데이터를 통지하는 예
 */
public class OnErrorResumeItemSample {
    public static void main(String[] args) {
        // just 메소드에 인자 데이터를 통지하는 Flowable을 생성
        Flowable.just(1, 3, 5, 0, 2, 4)
                // 받은 데이터로 100을 나눔
                .map(data -> 100 / data)
                // 에러가 발생하면 0을 통지
                //.onErrorReturnItem(0)
                .onErrorReturn(throwable -> {
                    if (throwable instanceof ArithmeticException) {
                        return -1;
                    }
                    return 0;
                })
                // 구독
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        System.out.println("data = " + data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("error = " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Complete");
                    }
                });

    }
}
