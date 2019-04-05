package reactivejava2.common;

public class Log {

    public static <T> void i(T t) {
        System.out.println(getThreadName() + " | value = " + t);
    }
    
    public static <T> void d(T t) { 
        System.out.println(getThreadName() + " | debug = " + t);
    }
    
    public static <T> void v(T t) {
        System.out.println(getThreadName() + " | " + t);
    }
    
    public static <T> void e(T t) {
        System.out.println(getThreadName() + " | error = " + t);
    }
    
    public static <T> void it(T t) {
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(getThreadName() + " | " + time + " | " + "value = " + t);
    }
    
    public static void dt(Object obj) { 
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(getThreadName() + " | " + time + " | " + "debug = " + obj);          
    }

    public static void et(Object obj) { 
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(getThreadName() + " | " + time + " | " + "error = " + obj);          
    }
    
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }
}
