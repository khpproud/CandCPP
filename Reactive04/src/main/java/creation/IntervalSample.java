package creation;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * interval(time, unit) 예제
 */
public class IntervalSample {
    public static void main(String[] args) throws InterruptedException {
        // "시:분:초:밀리초" 문자열로 변환하는 formatter
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss:SSS");

        // 1000ms마다 숫자를 통지하는 Flowable을 생성
        Flowable<Long> flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS, Schedulers.single());

        // 처리 작업을 시작하기전 시작
        System.out.println("시작 시각 : " + LocalTime.now().format(formatter));

        // 구독
        flowable.subscribe(data -> {
            // 스레드 이름을 가져옴
            String threadName = Thread.currentThread().getName();
            // 현재 시각의 "시:분:초:밀리초"를 가져옴
            String time = LocalTime.now().format(formatter);
            // 출력
            System.out.println(threadName + " : " + time + " : data = " + data);
        });

        // 잠시 기다림
        Thread.sleep(5000L);
    }
}
