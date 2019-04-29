import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

//onSubscribe 메소드에서 request 메서드를 호출하는 예
public class RequestMethod {
    public static void main(String[] args) {
        Flowable
                // 인자 데이터를 통지
                .just(1, 2, 3)
                // 구독
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    protected void onStart() {
                        System.out.println("onStart Start");
                        // 내부에서 request 메서드를 호출
                        super.onStart();
                        // 모든 작업이 완료된 후에 아래 메서드가 호출되므로 초기화 같은 작업의 순서에 주의
                        System.out.println("onStart End");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}
