import io.reactivex.Flowable;

public class MethodChain {
    public static void main(String[] args) {
        // 숫자 1 ~ 10 까지 데이터 생성
        Flowable<Integer> flowable = Flowable.range(1, 10)
                .filter(data -> data % 2 == 0)
                .map(i -> i * 100);

        // 구독후 받은 데이터 출력
        flowable.subscribe(System.out::println);
    }
}
