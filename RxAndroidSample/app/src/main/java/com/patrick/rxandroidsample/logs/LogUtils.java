package com.patrick.rxandroidsample.logs;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LogUtils {
    private static LogUtils sInstance;

    private static LogAdapter sLogAdapter;
    private static List<String> sLogs;

    private LogUtils() { }

    private static LogUtils init() {
        if (sInstance == null) {
            sInstance = new LogUtils();
        }
        return sInstance;
    }

    public static void log(String log) {
        sLogs.add(log);
        sLogAdapter.clear();
        sLogAdapter.addAll(sLogs);
    }

    public static void setupLogger(ListView listView) {
        init();
        sLogs = new ArrayList<>();
        sLogAdapter = new LogAdapter(listView.getContext(), new ArrayList<>());
        listView.setAdapter(sLogAdapter);
    }
}
