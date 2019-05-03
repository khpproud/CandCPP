import debug.DebugSubscriber;
import io.reactivex.processors.ReplayProcessor;

/**
 * ReplayProcessor 예 : 통지한 데이터를 모두 혹은 지정한 범위 까지 캐시하고 구독시점에 바로 캐시된 데이터를 모두 전송
 */
public class ReplayProcessorSample {
    public static void main(String[] args) {
        // Processor를 생성 : 캐시 크기는 2로 설정
        ReplayProcessor<Integer> processor = ReplayProcessor.createWithSize(2);

        // 통지 전에 Subscriber가 구독
        processor.subscribe(new DebugSubscriber<>("No.1"));

        // 데이터를 통지
        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);

        // 다른 SUbscriber도 구독
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
