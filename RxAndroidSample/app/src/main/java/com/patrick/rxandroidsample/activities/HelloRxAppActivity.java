package com.patrick.rxandroidsample.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

// 메모리 누수 해결 방법 2 : RxLifecycle 라이브러리 이용
public class HelloRxAppActivity extends RxAppCompatActivity {

    @BindView(R.id.textView)
    TextView mTextView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_v1);
        mUnbinder = ButterKnife.bind(this);

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("Hello RxJava!!!");
            emitter.onComplete();
        })

                //.compose(bindToLifecycle())
                    .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(mTextView::setText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
