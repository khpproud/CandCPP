package com.patrick.rxandroidsample.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;
import com.patrick.rxandroidsample.logs.LogAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class LoopActivity extends AppCompatActivity {
    private static final String TAG = LoopActivity.class.getSimpleName();

    @BindView(R.id.lv_log)
    ListView mLogView;

    @BindView(R.id.tv_title)
    TextView mTitle;

    private Unbinder mUnbinder;
    private LogAdapter mLogAdapter;
    private List<String> mLogs;

    Iterable<String> samples = Arrays.asList("banana", "orange", "apple","mango", "pineapple",
            "melon", "watermelon");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);

        mUnbinder = ButterKnife.bind(this);

        setupLogger();
        setSampleTitle();
    }

    private void setSampleTitle() {
        mTitle.append(
                Observable.fromIterable(samples)
                .reduce((r, s) -> (r + "\n") + s).blockingGet()
        );
    }

    // Java
    @OnClick(R.id.btn_loop)
    void loop() {
        log(">>>> get an apple :: java");
        for (String s: samples) {
            if (s.contains("apple")) {
                log(s);
                break;
            }
        }
    }

    // RxJava 1.X
    @OnClick(R.id.btn_loop2)
    void loop2() {
        log(">>>> get an apple :: RxJava 1.x");
        rx.Observable.from(samples)
                .filter(s -> s.contains("apple"))
                .firstOrDefault("Not found")
                .subscribe(this::log);
    }

    // RxJava 2.X
    @SuppressLint("CheckResult")
    @OnClick(R.id.btn_loop3)
    void loop3() {
        log(">>>> get an apple :: RxJava 2.x");
        Observable.fromIterable(samples)
                .filter(s -> s.contains("apple"))
                .first("Not found")
                .subscribe(this::log);
    }

    // 로거 초기화
    private void setupLogger() {
        mLogs = new ArrayList<>();
        mLogAdapter = new LogAdapter(this, new ArrayList<>());
        mLogView.setAdapter(mLogAdapter);
    }

    // 로깅
    private void log(String s) {
        mLogs.add(s);
        mLogAdapter.clear();
        mLogAdapter.addAll(mLogs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();;
    }
}
