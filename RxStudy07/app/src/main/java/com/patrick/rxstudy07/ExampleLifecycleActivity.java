package com.patrick.rxstudy07;

import android.os.Bundle;
import android.widget.TextView;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static com.patrick.rxstudy07.LogUtil.*;

/**
 * RxLifecycle 적용 예
 */
public class ExampleLifecycleActivity extends RxAppCompatActivity {
    BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @BindView(R.id.example_hello_id)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        ButterKnife.bind(this);

//        Observable.interval(1, TimeUnit.SECONDS)
//                .doOnDispose(() -> log("Disposed"))
//                .compose(bindToLifecycle())
//                .subscribe(i -> log("subscribe()"));

//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(10)
//                .doOnDispose(() -> log("Disposed()"))
//                .subscribe(i -> log("subscribe()"));

        Observable.interval(1, TimeUnit.SECONDS)
                .compose(RxLifecycleAndroid.bindView(mTextView))
                .doOnDispose(() -> log("Disposed"))
                .subscribe(i -> log("subscribe()"));
        //lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }
}
