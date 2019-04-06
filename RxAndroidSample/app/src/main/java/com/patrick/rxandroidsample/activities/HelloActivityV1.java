package com.patrick.rxandroidsample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

// 메모리 누수 해결 방법 1 : Disposable 인터페이스를 이용하여 명시적 자원 해제
public class HelloActivityV1 extends AppCompatActivity {
    private static final String TAG = HelloActivityV1.class.getSimpleName();

    @BindView(R.id.textView)
    TextView mTextView;

    private Disposable mDisposable;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_v1);

        mUnbinder = ButterKnife.bind(this);

        DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                mTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mDisposable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("Hello RxAndroid!!!");
            emitter.onComplete();
        }).subscribeWith(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mDisposable.isDisposed())
            mDisposable.dispose();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
