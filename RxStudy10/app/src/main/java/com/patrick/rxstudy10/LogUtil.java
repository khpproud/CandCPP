package com.patrick.rxstudy10;

import android.util.Log;

public class LogUtil {
    private LogUtil() { }

    public static void plog(String stage, Object item) {
        System.out.println(stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    public static void plog(String stage) {
        System.out.println(stage + " : " + Thread.currentThread().getName());
    }

    public static void plog(Throwable e) { System.err.println("Error on " +
            Thread.currentThread().getName()  + " : " + e); }

    public static void plog(String stage, Throwable e) { System.err.println(stage + ", Error on " +
            Thread.currentThread().getName()  + " : " + e); }

    public static void log(String stage, Object item) {
        Log.d("APP", stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    public static void log(String stage) {
        Log.d("APP", stage + " : " + Thread.currentThread().getName());
    }

    public static void log(Throwable e) { Log.e("APP", "Error : " + e.getMessage()); }
}
