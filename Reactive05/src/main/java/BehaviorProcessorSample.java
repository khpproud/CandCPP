import debug.DebugSubscriber;
import io.reactivex.processors.BehaviorProcessor;

/**
 * BehaviorProcessor 예 : 마지막으로 통지한 데이터를 캐시하고 구독시 캐시된 데이터를 소비자에게 통지
 */
public class BehaviorProcessorSample {
    public static void main(String[] args) {
        // Processor를 생성 - defaultValue 설정
        BehaviorProcessor<Integer> processor = BehaviorProcessor.createDefault(0);

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
