package reactivejava2.common;

import java.util.Random;

public class CommonUtils {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // 실행 시간을 표기하기 위한 정적 변수
    public static long startTime;
    
    public static void exampleStart() {
        startTime = System.currentTimeMillis();
    }
    
    public static void exampleComplete() { 
        System.out.println("-----------------------");
    }
    
    public static void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void doSomething() {
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // 해당 하는 인덱스의 숫자를 알파벳으로 변환
    public static String numberToAlphabet(long x) {
        return Character.toString(ALPHABET.charAt((int) x % ALPHABET.length()));
    }
}
