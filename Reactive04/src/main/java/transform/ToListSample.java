package transform;

import debug.DebugSingleObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

/**
 * toList() 예제
 */
public class ToListSample {
    public static void main(String[] args) {
        Single<List<Integer>> single =
                // Flowable을 생성
                Flowable.just(1, 2, 3, 4, 5, 6)
                // 전체 데이터를 담은 리스트를 통지
                .toList();

        // 구독
        single.subscribe(new DebugSingleObserver<>());

        // Single을 통지하므로 데이터 통지만으로 처리가 끝나며 완료 통지를 하지 않음
    }
}
