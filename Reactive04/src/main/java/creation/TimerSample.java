package creation;

import io.reactivex.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * timer(time, unit) 예제
 */
public class TimerSample {
    public static void main(String[] args) throws InterruptedException {
        // "시:분:초:밀리초" 문자열로 변환하는 포매터
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss:SSS");

        // 처리 시작 시각 출력
        System.out.println("시작 시각 : " + LocalTime.now().format(formatter));

        // 1000ms 뒤에 숫자 '0'을 통지하는 Flowable을 생성
        Flowable<Long> flowable = Flowable.timer(1000L, TimeUnit.MILLISECONDS);

        // 구독
        flowable.subscribe(data -> {
            // 첫 번째 인자: 데이터 통지 시
            // 스레드 이름을 얻음
            String threadName = Thread.currentThread().getName();
            // 현재 시각을 "시:분:초:밀리초" 형태로 출력
            String time = LocalTime.now().format(formatter);
            // 출력
            System.out.println(threadName + " : " + time + " : data = " + data);
        },
                // 두 번째 인자 : 에러 통지 시
                error -> System.err.println("Error = " + error),
                // 세 번째 인자 : 완료 통지 시
                () -> System.out.println("Complete")
        );

        // 잠시 기다림
        Thread.sleep(2000L);
    }
}
