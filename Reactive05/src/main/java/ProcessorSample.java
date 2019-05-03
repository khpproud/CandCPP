import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Processor가 데이터를 받아 통지하는 예제
 */
public class ProcessorSample {
    public static void main(String[] args) {
        // Processor를 생성
        Processor<Integer, Integer> processor = PublishProcessor.create();

        // Processor를 구독
        processor.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("에러 발생 : " + t);
            }

            @Override
            public void onComplete() {
                System.out.println("완료");
            }
        });

        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);
        processor.onComplete();
    }
}
