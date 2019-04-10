package com.patrick.rxstudy07;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.patrick.rxstudy07.LogUtil.log;

/**
 * 구독 정리에 Disposable 사용 하는 예
 */
public class StubActivity extends AppCompatActivity {
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> log("subscribe()"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null)
            mDisposable.dispose();
    }
}
