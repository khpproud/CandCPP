import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.List;

/**
 * Iterator 패턴의 컨셉을 가져온 RxJava의 Flowable의 데이터 처리
 */
public class FlowableSample {
    public static void main(String[] args) {
        // 리스트로 Flowable을 생성
        List<String> list = Arrays.asList("a", "b", "c");
        Flowable<String> flowable = Flowable.fromIterable(list);

        // 처리를 시작
        flowable.subscribe(System.out::println);
    }
}
