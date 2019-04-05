package com.patrick.rxandroidsample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class HelloActivityV2 extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView mTextView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_v2);

        mUnbinder = ButterKnife.bind(this);

        // 람다 표현식으로 textView 설정
//        Observable.<String>create(emitter -> {
//            emitter.onNext("Hello, RxAndroid!!!");
//            emitter.onComplete();
//        }).subscribe(s -> mTextView.setText(s));

        // just() 함수 사용
        Observable.just("Hello RxAndroid!!!")
                .subscribe(mTextView::setText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
