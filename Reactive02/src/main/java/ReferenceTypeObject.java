/**
 * final 제한자가 붙은 클래스의 인스턴스는 참조 변경은 불가하지만 내부 필드는 변경이 가능
 */
public class ReferenceTypeObject {
    private String value = "A";

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
