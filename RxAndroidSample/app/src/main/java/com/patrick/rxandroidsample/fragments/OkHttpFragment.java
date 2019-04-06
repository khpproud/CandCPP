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
import com.patrick.rxandroidsample.square.Contributor;
import com.patrick.rxandroidsample.square.GitHubServiceApi;
import com.patrick.rxandroidsample.square.RestfulAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OkHttpFragment extends Fragment {
    private static final String OWNER = "khpproud";
    private static final String REPO = "RxJava";

    @BindView(R.id.ohf_lv_log)
    ListView mLogView;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_okhttp, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        LogUtils.setupLogger(mLogView);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
    }

    @OnClick(R.id.ohf_btn_retrofit)
    void getRetrofit() {
        startRetrofit();
    }

    @OnClick(R.id.ohf_btn_get_retrofit_okhttp)
    void getOkHttp() {
        startOkHttp();
    }

    @OnClick(R.id.ohf_btn_get_retrofit_okhttp_rx)
    void getRx() {
        startRx();
    }

    // Retrofit + OkHttp(Retrofit Call 의 내부)
    private void startRetrofit() {
        startCall(RestfulAdapter.getInstance().getSimpleApi());
    }

    // Retrofit + OkHttp
    private void startOkHttp() {
        startCall(RestfulAdapter.getInstance().getServiceApi());
    }

    private void startCall(GitHubServiceApi service) {
        Call<List<Contributor>> call = service.getCallContributors(OWNER, REPO);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contributor>> call,
                                   @NonNull Response<List<Contributor>> response) {
                if (response.isSuccessful()) {
                    // 응답 성공
                    @NonNull List<Contributor> contributors = response.body();
                    for (Contributor c : contributors) {
                        LogUtils.log(c.toString());
                    }
                } else {
                    LogUtils.log("not successful!!!");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                LogUtils.log("Fail : " + t.getMessage());
            }
        });
    }

    // Retrofit + OkHttp + RxJava
    private void startRx() {
        GitHubServiceApi service = RestfulAdapter.getInstance().getServiceApi();
        Observable<List<Contributor>> observable = service.getObContributors(OWNER, REPO);

        mCompositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        contributors -> {
                            for (Contributor c : contributors) {
                                LogUtils.log(c.toString());
                            }
                        },
                        e -> LogUtils.log("Error : " + e.getMessage()),
                        () -> LogUtils.log("Complete")));
    }
}
