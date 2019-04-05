package com.patrick.rxandroidsample.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class HelloActivityV3 extends RxAppCompatActivity {
    @BindView(R.id.textView)
    TextView mTextView;

    private Unbinder mUnbinder;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_v1);

        mUnbinder = ButterKnife.bind(this);

        Observable.just("Hello RxWorld!!!")
                .compose(bindToLifecycle())
                .subscribe(mTextView::setText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
