import io.reactivex.Flowable;

public class FunctionTiming {
    public static void main(String[] args) throws InterruptedException {
        // 값을 전달받을 때
        Flowable<Long> flowable = Flowable.just(System.currentTimeMillis());

        // 함수형 인터페이스를 전달받은 경우
        Flowable<Long> flowable2 = Flowable.fromCallable(System::currentTimeMillis);

        flowable.subscribe(time -> System.out.println("Time1 : " + time.toString()));
        flowable2.subscribe(time -> System.out.println("Time2 : " + time.toString()));

        // 잠시 후
        Thread.sleep(1000);

        // 다시 시간 출력
        flowable.subscribe(time -> System.out.println("Time1 : " + time.toString()));
        flowable2.subscribe(time -> System.out.println("Time2 : " + time.toString()));
    }
}
