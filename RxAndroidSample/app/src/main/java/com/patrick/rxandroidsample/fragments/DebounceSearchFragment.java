package com.patrick.rxandroidsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.patrick.rxandroidsample.R;
import com.patrick.rxandroidsample.logs.LogUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * 추천 검색어 기능 예(debounce 활용)
 */
public class DebounceSearchFragment extends Fragment {
    private Unbinder mUnbinder;
    private Disposable mDisposable;

    @BindView(R.id.dsf_input_deb_search)
    EditText mSearchBox;

    @BindView(R.id.dsf_lv_log)
    ListView mLogView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_debounce_search, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        LogUtils.setupLogger(mLogView);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
        mDisposable.dispose();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mDisposable = getObservable()
//                .debounce(500, TimeUnit.MILLISECONDS)
//                .filter(s -> !TextUtils.isEmpty(s))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(getObserver());

        mDisposable = RxTextView.textChangeEvents(mSearchBox)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(s -> !TextUtils.isEmpty(s.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserverLib());
    }

    private Observable<CharSequence> getObservable() {
        return Observable.create(emitter -> mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emitter.onNext(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
    }

    private DisposableObserver<CharSequence> getObserver() {
        return new DisposableObserver<CharSequence>() {
            @Override
            public void onNext(CharSequence word) {
                LogUtils.log("Search " + word);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.log("Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.log("Complete");
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> getObserverLib() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent view) {
                LogUtils.log("Search " + view.text().toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.log("Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.log("Complete");
            }
        };
    }
}
