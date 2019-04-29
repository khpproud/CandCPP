import java.util.Date;

/**
 * 불변 객체를 다루는 예 -> 객체에 직접적인 접근을 막고 복사본을 이용해 변경 및 참조 함으로서
 * 불변성을 유지
 */
public final class ImmutableObject {
    private final Date value;

    public ImmutableObject(Date value) {
        // 가변적인 Date가 변경돼도 영향이 없도록 복제한 값을 설정
        this.value = (Date) value.clone();
    }

    public Date getValue() {
        // 변환값 Date가 외부에서 변경돼도 영향이 없도록 복제한 값을 반환
        return (Date) value.clone();
    }

    // 직접 객체의 상태를 변경하는 것이 아닌 대상 필드만 변경한 복사본을 생성해 원래 객체에는 영향을 미치지 않음
    public ImmutableObject changeValue(Date value) {
        return new ImmutableObject(value);
    }
}
