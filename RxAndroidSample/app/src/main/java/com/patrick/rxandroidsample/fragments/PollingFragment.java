package com.patrick.rxandroidsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.patrick.rxandroidsample.R;
import com.patrick.rxandroidsample.logs.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PollingFragment extends Fragment {
    private static final Long INITIAL_DELAY = 0L;
    private static final Long PERIOD = 3L;

    @BindView(R.id.lv_polling_log)
    ListView mLogView;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_polling, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        LogUtils.setupLogger(mLogView);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    @OnClick(R.id.btn_polling)
    void polling() {
        startPollingV1();
    }

    @OnClick(R.id.btn_polling2)
    void polling2() {
        startPollingV2();
    }

    private void startPollingV1() {
        Observable<String> ob = Observable.interval(INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
                .flatMap(o -> Observable.just("polling #1 " + o.toString()));

        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LogUtils::log);
    }

    // repeatWhen() 과 delay() 함수를 이용하여 변경
    private void startPollingV2() {
        Observable<String> ob = Observable.just("polling #2")
                .repeatWhen(o -> o.delay(PERIOD, TimeUnit.SECONDS));

        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LogUtils::log);
    }

}
