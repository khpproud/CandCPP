import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator 패턴 사용 예
 */
public class IteratorSample {
    public static void main(String[] args) {
        // 리스트에서 Iterator를 얻음
        List<String> list = Arrays.asList("a", "b", "c");
        Iterator<String> iterator = list.iterator();

        // 받을 데이터가 남아있는지 확인
        while (iterator.hasNext()) {
            // 데이터를 얻음
            String value = iterator.next();
            // 얻은 데이터를 출력
            System.out.println(value);
        }
    }
}
