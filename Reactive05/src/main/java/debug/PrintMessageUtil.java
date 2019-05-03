package debug;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PrintMessageUtil {
    private PrintMessageUtil() { }

    public static <T> void next(String label, T data) {
        printMessage(label, data.toString());
    }

    public static <T> void error(String label, T error) {
        printMessage(label, "Error = " + error);
    }

    public static <T> void complete(String label) {
        printMessage(label, "Complete");
    }

    public static <T> void start(String label) {
        printMessage(label, "Start");
    }

    public static <T> void nextT(String label, T data) {
        printMessageWithTime(label, data.toString());
    }

    public static <T> void errorT(String label, T error) {
        printMessageWithTime(label, "Error = " + error);
    }

    public static <T> void completeT(String label) {
        printMessageWithTime(label, "Complete");
    }

    public static <T> void startT(String label) {
        printMessageWithTime(label, "Start");
    }

    private static String getThreadName() {
        return Thread.currentThread().getName();
    }

    private static void printMessage(String label, String state) {
        String threadName = getThreadName();
        if (label == null) {
            System.out.println(threadName + " : " + state);
        } else {
            System.out.println(threadName + " : " + label + " : " + state);
        }
    }

    private static void printMessageWithTime(String label, String state) {
        String threadName = getThreadName();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss:SSS");
        String time = LocalTime.now().format(formatter);
        if (label == null) {
            System.out.println(threadName + " [" + time + "] : " + state);
        } else {
            System.out.println(threadName + " [" + time + "] : " + label + " : " + state);
        }
    }
}
