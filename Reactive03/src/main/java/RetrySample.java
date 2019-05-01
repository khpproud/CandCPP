import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Random;

/**
 * 재시도 하는 예제
 */
public class RetrySample {
    public static void main(String[] args) {
        final Random random = new Random();

        // 처리 시작과 종료 시 메시지를 출력하고 1부터 3까지 숫자를 통지하는 Flowable을 create 메서드로 생성
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
            // Flowable 처리를 시작
            System.out.println("Flowable 처리 시작");
            // 통지 처리
            for (int i = 0; i < 3; i++) {
                // 데이터가 2일 때 에러가 랜덤하게 발생
                if (i == 2 && random.nextInt(2) % 2 == 0) {
                    throw new Exception("예외 발생!!!");
                }
                // 데이터를 통지
                emitter.onNext(i);
            }
            // 완료를 통지
            emitter.onComplete();
            // Flowable 처리를 완료
            System.out.println("Flowable 처리 완료");
        }, BackpressureStrategy.BUFFER)
                /*
                 * retry 메서드 앞에 doOnSubscribe 메서드를 설정해 재시도로 구독을 시작할 때
                 * 메시지가 출력되서 처리 작업이 처음부터 다시 시작함을 확인
                 */
                .doOnSubscribe(subscription -> System.out.println("flowable : doOnSubscribe"))
                // 에러가 발생하면 2번 까지 재시도
                .retry(2);

        // 구독
        flowable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("subscriber: onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("data = " + data);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error = " + t);
            }

            @Override
            public void onComplete() {
                System.out.println("종료");
            }
        });
    }
}
