import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import java.time.DayOfWeek;
import java.time.LocalDate;

// Single - 데이터를 1건만 통지하거나 에러를 통지(완료 통지가 별도로 없음)
public class SingleSample {
    public static void main(String[] args) {
        // Single 생성 - emitter의 onSuccess()에 통지하는 데이터를 설정
        Single<DayOfWeek> single = Single.create(emitter -> emitter.onSuccess(LocalDate.now().getDayOfWeek()));

        // 구독
        single.subscribe(new SingleObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 아무것도 하지 않음
            }

            @Override
            public void onSuccess(DayOfWeek dayOfWeek) {
                // Single에서 통지하는 데이터를 전달받아 출력
                System.out.println(dayOfWeek);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
