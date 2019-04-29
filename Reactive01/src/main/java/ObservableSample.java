// 옵저버블 사용 예

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ObservableSample {
    public static void main(String[] args) throws InterruptedException {
        // 인사말을 통지하는 Observable 생성
        Observable<String> observable = Observable.create(emitter -> {
            // 통지 데이터
            String[] datas = { "Hello, World!", "Hi, RxJava!", "Good people!" };

            for (String data : datas) {
                // 구독이 해지되면 처리를 중단
                if (emitter.isDisposed()) {
                    System.out.println("Subscription is disposed!");
                    return;
                }

                // 데이터를 통지
                emitter.onNext(data);
            }

            // 완료를 통지
            emitter.onComplete();
        });

        observable
                // 소비하는 측의 처리를 개별 스레드로 실행
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe((String s) -> System.out.println(Thread.currentThread().getName() + " : " + s),
                        (Throwable e) -> e.printStackTrace(),
                        () -> System.out.println(Thread.currentThread().getName() + " : Completed!"));

        // 잠시 기다림
        Thread.sleep(500L);
    }
}
