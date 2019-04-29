import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReferenceTypeTest {
    @Test
    public void final로선언해도상태변경이가능한예제() {
        // final이 붙은 참조 변수
        final ReferenceTypeObject instance = new ReferenceTypeObject();

        // 참조를 변경하면 컴파일 에러
        //instance = new ReferenceTypeTest();

        // 변경 전 상태를 확인
        assertThat(instance.getValue(), is("A"));

        // instance의 상태를 변경할 수 있음
        instance.setValue("B");

        // 상태를 변경한 객체를 확인
        assertThat(instance.getValue(), is("B"));
    }
}
