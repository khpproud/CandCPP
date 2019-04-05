package com.patrick.rxandroidsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;
import com.patrick.rxandroidsample.R;
import com.patrick.rxandroidsample.logs.LogUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

public class OnClickFragment extends Fragment {
    public static final String TAG = OnClickFragment.class.getSimpleName();

    // 임의의 로컬 키라 가정
    private static final int SEVEN = 7;

    private Unbinder mUnbinder;

    @BindView(R.id.lv_log)
    ListView mLogView;
    @BindView(R.id.btn_click_observer)
    Button mButton;
    @BindView(R.id.btn_click_observer_lambda)
    Button mButtonLambda;
    @BindView(R.id.btn_click_observer_binding)
    Button mButtonBinding;
    @BindView(R.id.btn_click_observer_extra)
    Button mButtonExtra;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_on_click, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        LogUtils.setupLogger(mLogView);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
        mUnbinder = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getClickEventObservable()
                .map(s -> "clicked")
                .subscribe(getObserver());

        getClickEventObservableWithLambda()
                .map(s -> "clicked lambda")
                .subscribe(LogUtils::log);

        getClickEventObservableWithRxBinding()
                .subscribe(LogUtils::log);

        getClickEventObservableExtra()
                .map(local -> SEVEN)
                .flatMap(this::compareNumbers)
                .subscribe(LogUtils::log);
    }

    private Observable<View> getClickEventObservable() {
        return Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(ObservableEmitter<View> emitter) throws Exception {
                mButton.setOnClickListener(emitter::onNext);
            }
        });
    }

    private Observable<View> getClickEventObservableWithLambda() {
        return Observable.create(e -> mButtonLambda.setOnClickListener(e::onNext));
    }

    private Observable<String> getClickEventObservableWithRxBinding() {
        return RxView.clicks(mButtonBinding)
                .map(e -> "Clicked RxBinding");
    }

    private Observable<View> getClickEventObservableExtra() {
        return Observable.create(e -> mButtonExtra.setOnClickListener(e::onNext));
    }

    // 랜덤으로 생성한 임의의 서버 키와 로컬 카를 비교한다 가정
    private Observable<String> compareNumbers(int input) {
        return Observable.just(input)
                .flatMap(local -> {
                    Random random = new Random();
                    int remote = random.nextInt(10);
                    return Observable.just("local : " + local, "remote : " + remote,
                            "result : " + (local == remote));
                });
    }

    private DisposableObserver<? super String> getObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                LogUtils.log(s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LogUtils.log("error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.log("complete");
            }
        };
    }
}
