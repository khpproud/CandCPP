package com.patrick.rxandroidsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.patrick.rxandroidsample.R;
import com.patrick.rxandroidsample.logs.LogUtils;
import com.patrick.rxandroidsample.volley.LocalVolley;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VolleyFragment extends Fragment {
    // sample url
    private static final String URL = "http://time.jsontest.com";

    @BindView(R.id.vf_lv_log)
    ListView mLogView;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_volley, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        LogUtils.setupLogger(mLogView);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnbinder != null)
            mUnbinder.unbind();
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
    }

    @OnClick(R.id.vf_btn_get)
    void getTime() {
        post(getObservable());
    }

    @OnClick(R.id.vf_btn_get2)
    void getTimeCallable() {
        post(getObservableFromCallable());
    }

    @OnClick(R.id.vf_btn_get3)
    void getTimeFuture() {
        post(getObservableFromFuture());
    }

    private void post(Observable<JSONObject> observable) {
        DisposableObserver<JSONObject> observer = getObserver();
        mCompositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    // RequestObject 생성, RequestQueue 에 추가, 콜백 등록하기
    private RequestFuture<JSONObject> getFuture() {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        // 콜백을 requestFuture로 설정하여 성공, 에러 모두 future 갹체로 받음
        JsonObjectRequest req = new JsonObjectRequest(URL, null, future, future);
        LocalVolley.getRequestQueue().add(req);
        return future;
    }

    private JSONObject getData() throws ExecutionException, InterruptedException {
        return getFuture().get();
    }

    // 시간 정보 얻기 1 (defer())
    private Observable<JSONObject> getObservable() {
        // 내부적으로 예외 처리를 하지 못하기 때문에 just()로 한 번 감쌈
        return Observable.defer(() -> {
            try {
                return Observable.just(getData());
            } catch (InterruptedException e) {
                LogUtils.log("Error : " + e.getMessage());
                return Observable.error(e);
            } catch (ExecutionException e) {
                LogUtils.log("Error : " + e.getCause());
                return Observable.error(e.getCause());
            }
        });
    }

    // 시간 정보 얻기 2 (fromCallable()) defer() + just() 같은 효과 제공
    private Observable<JSONObject> getObservableFromCallable() {
        return Observable.fromCallable(this::getData);
    }

    // 시간 정보 얻기 3 (fromFuture()) Future 객체 자체를 내부에서 바로 처리
    private Observable<JSONObject> getObservableFromFuture() {
        return Observable.fromFuture(getFuture());
    }

    private DisposableObserver<JSONObject> getObserver() {
        return new DisposableObserver<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) {
                LogUtils.log(">> " + jsonObject.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.log("Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.log("Complete...");
            }
        };
    }
}
