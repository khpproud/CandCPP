import io.reactivex.functions.Action;

/**
 * 익명클래스와 람다식의 this 가 가리키는 대상의 차이 예
 */
public class DifferenceOfThisSample {
    public static void main(String[] args) throws Exception {
        DifferenceOfThisSample target = new DifferenceOfThisSample();
        target.execute();
    }

    /** 익명 클래스와  람다식의 this 출력 */
    private void execute() throws Exception {
        // 익명 클래스
        Action anonymous = new Action() {
            @Override
            public void run() {
                System.out.println("익명 클래스의 this : " + this);
            }
        };

        // 람다식
        Action lambda = () -> System.out.println("람다식의 this : " + this);

        // 각각 실행
        anonymous.run();
        lambda.run();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
