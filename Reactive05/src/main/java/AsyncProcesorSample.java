import debug.DebugSubscriber;
import io.reactivex.processors.AsyncProcessor;

/**
 * AsyncProcessor 예 : 완료할 때까지 아무것도 통지하지 않다가 완료 했을 때 마지막 데이터와 완료를 통지
 */
public class AsyncProcesorSample {
    public static void main(String[] args) {
        // Processor를 생성
        AsyncProcessor<Integer> processor = AsyncProcessor.create();

        // 통지 전에 Subscriber가 구독
        processor.subscribe(new DebugSubscriber<>("No.1"));

        // 데이터를 통지
        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);

        // 다른 Subscriber가 구독
        System.out.println("Subscriber No.2 추가");
        processor.subscribe(new DebugSubscriber<>("--- No.2"));

        // 데이터를 통지
        processor.onNext(4);
        processor.onNext(5);

        // 완료를 통지
        processor.onComplete();

        // 완료 후에도 다른 Subscriber가 구독
        System.out.println("Subscriber No.3 추가");
        processor.subscribe(new DebugSubscriber<>("------ No.3"));
    }
}
