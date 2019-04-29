// Flowable의 처리 흐름을 살펴보는 예

import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableSample {

    public static void main(String[] args) throws InterruptedException {
        // 인사말을 통지하는 Flowable을 생성
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                String[] datas = { "Hello, world!", "Hi, RxJava!", "Good People!" };

                for (String data : datas) {
                    // 구독이 해지되면 처리를 중단
                    if (emitter.isCancelled())
                        return;
                    // 데이터를 통지
                    emitter.onNext(data);
                    System.out.println(Thread.currentThread().getName() + " : " +  data + ", emitted!!");
                }

                // 완료를 통지
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);            // 초과한 데이터는 버퍼링됨

        flowable
                // Subscriber 처리를 개별 스레드에서 실행
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe(new Subscriber<String>() {
                    // 데이터 개수 요청과 구독 해지를 하는 객체
                    private Subscription subscription;

                    // 구독 시작시 처리
                    @Override
                    public void onSubscribe(Subscription s) {
                        // Subscription을 Subscriber에 보관
                        this.subscription = s;
                        // 받을 데이터 개수를 요청
                        this.subscription.request(1);
                        // 데이터 개수를 제한하지 않고 데이터를 통지하게 요청
                        //this.subscription.request(Long.MAX_VALUE);
                    }

                    // 데이터를 받을 때 처리
                    @Override
                    public void onNext(String s) {
                        // 받은 데이터를 출력
                        System.out.println(Thread.currentThread().getName() + " : " + s);
                        // 다음에 받을 데이터 개수를 요청
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    // 완료 통지시 처리
                    @Override
                    public void onComplete() {
                        // 완료 출력
                        System.out.println(Thread.currentThread().getName() + " : " + "Completed!");
                    }
                });
        // 잠시 기다림
        Thread.sleep(500);
    }
}
