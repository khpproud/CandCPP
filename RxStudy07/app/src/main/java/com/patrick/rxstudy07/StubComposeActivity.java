package com.patrick.rxstudy07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.patrick.rxstudy07.LogUtil.*;

public class StubComposeActivity extends AppCompatActivity {
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        mCompositeDisposable = new CompositeDisposable();

        Disposable disposable1 = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> log("subscribe1()"));
        Disposable disposable2 = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> log("subscribe2()"));
        Disposable disposable3 = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(i -> log("subscribe3()"));

        mCompositeDisposable.addAll(
                disposable1, disposable2, disposable3
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }
}
