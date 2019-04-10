package com.patrick.rxstudy05;

public class LogUtil {
    public static void plog(String stage, String item) {
        System.out.println(stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    public static void plog(String stage) {
        System.out.println(stage + " : " + Thread.currentThread().getName());
    }

    public static void plog(Throwable e) { System.err.println("Error : " + e.getMessage()); }
}
