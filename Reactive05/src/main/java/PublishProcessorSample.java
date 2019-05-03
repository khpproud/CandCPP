import debug.DebugSubscriber;
import io.reactivex.processors.PublishProcessor;

/**
 * PublishProcessor 예 : 이미 통지한 데이터를 캐시하지 않고 구독한 뒤로 받은 데이터만 통지
 */
public class PublishProcessorSample {
    public static void main(String[] args) {
        // Processor를 생성
        PublishProcessor<Integer> processor = PublishProcessor.create();

        // 통지 전에 Subscriber가 구독
        processor.subscribe(new DebugSubscriber<>("No.1"));

        // 데이터를 통지
        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);

        // 다른 Subscriber도 구독
        System.out.println("Subscriber No.2 추가");
        processor.subscribe(new DebugSubscriber<>("--- No.2"));

        // 데이터를 통지
        processor.onNext(4);
        processor.onNext(5);

        // 완료를 통지
        processor.onComplete();

        // 완료 후에도 다른 subscriber가 구독
        System.out.println("Subscriber No.3 추가");
        processor.subscribe(new DebugSubscriber<>("------ No.3"));
    }
}
