import debug.DebugSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.UnicastProcessor;
import org.reactivestreams.Subscriber;

/**
 * UnicastProcessor 예 : 1개의 소비자만 구독할 수 있음
 */
public class UnicastProcessorSample {
    public static void main(String[] args) {
        // Processor를 생성
        UnicastProcessor<Integer> processor = UnicastProcessor
                .create(3, () -> System.out.println("Canceled"));

        // 데이터를 통지
        processor.onNext(1);
        processor.onNext(2);

        // 도중에 Subscriber가 구독
        System.out.println("Subscriber No.1 추가");
        DebugSubscriber subscriber = new DebugSubscriber<>("No.1");
        processor.subscribe(subscriber);

        // 다른 Subscriber가 구독
        System.out.println("Subscriber No.2 추가");
        processor.subscribe(new DebugSubscriber<>("--- No.2"));

        // 데이터를 통지
        processor.onNext(3);

        // 구독자가 취소
        System.out.println("Subscriber No.1 구독 취소");
        subscriber.dispose();

        // 새로운 Subscriber가 구독
        System.out.println("Subscriber No.3 추가");
        processor.subscribe(new DebugSubscriber<>("------ No.3"));

        // 데이터를 통지
        processor.onNext(4);

        // 완료를 통지
        processor.onComplete();
    }
}
