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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

// 메모리 누수 해결 방법 3 : CompositeDisposable 클래스 이용
public class HelloActivityComposite extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView mTextView;

    private CompositeDisposable mCompositeDisposable;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_v1);
        mUnbinder = ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();

        Disposable disposable = Observable.create((ObservableEmitter<String> e) -> {
                    e.onNext("Hello world");
                    e.onComplete();
                }).subscribe(mTextView::setText);
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
        if (!mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
    }
}
