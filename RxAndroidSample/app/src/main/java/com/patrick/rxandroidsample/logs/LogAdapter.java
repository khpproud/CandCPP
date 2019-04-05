package com.patrick.rxandroidsample.logs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.patrick.rxandroidsample.R;

import java.util.List;

public class LogAdapter extends ArrayAdapter<String> {
    public LogAdapter(@NonNull Context context, List<String> logs) {
        super(context, R.layout.textview_log, R.id.tv_log, logs);
    }
}
