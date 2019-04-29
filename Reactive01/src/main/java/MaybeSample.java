import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

import java.time.DayOfWeek;

// Maybe - 데이터를 1건만 통지하거나 1건도 통지하지 않고 완료를 통지하거나 에러를 통지
public class MaybeSample {
    public static void main(String[] args) {
        // Maybe 생성 - 데이터 통지 없이 onComplete 통지
        Maybe<DayOfWeek> maybe = Maybe.create(MaybeEmitter::onComplete);

        // Maybe 생성 - 데이터를 1건만 통지
        //Maybe<DayOfWeek> maybe = Maybe.create(emitter -> emitter.onSuccess(LocalDate.now().getDayOfWeek()));

        // 구독
        maybe.subscribe(new MaybeObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 아무 것도 하지 않음
            }

            @Override
            public void onSuccess(DayOfWeek dayOfWeek) {
                System.out.println(dayOfWeek);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
