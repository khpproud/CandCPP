import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class Effected {
    // 계산 방법을 나타내는 enum 객체
    private enum State {
        ADD, MULTIPLY
    };

    // 계산 방법
    private static State calcMethod;

    public static void main(String[] args) throws InterruptedException {
        // 계산 방법을 설정
        calcMethod = State.ADD;

        Flowable<Long> flowable =
                // 300ms마다 데이터를 통지하는 Flowable을 생성
                Flowable.interval(300, TimeUnit.MILLISECONDS)
                // 7건 까지
                .take(7)
                // 각 데이터를 계산
                .scan((sum, data) -> {
                    if (calcMethod == State.ADD)
                        return sum + data;
                    else
                        return sum * data;
                });
        // 구독하고 받은 데이터를 출력
        flowable.subscribe(n -> System.out.println("result data : " + n));

        // 잠시 기다렸다가 계산 방법을 곱하기로 변경
        Thread.sleep(1000);
        System.out.println("계산 방법 변경");
        calcMethod = State.MULTIPLY;

        // 잠시 기다린다
        Thread.sleep(2000);
    }
}
