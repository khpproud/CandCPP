package com.patrick.rxandroidsample.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AsyncTaskActivity extends AppCompatActivity {
    private static final String TAG = AsyncTaskActivity.class.getSimpleName();

    @BindView(R.id.textViewMain)
    TextView mTextView;

    private Unbinder mUnbinder;

    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnbinder = ButterKnife.bind(this);
        //initAndroidAsync();
        initRxAsync();
    }

    // AsyncTask 초기화 및 실행
    private void initAndroidAsync() {
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Hello", "async", "world");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    // RxAndroid를 이용한 스케줄러
    private void initRxAsync() {
        Observable.just("Hello", "rx", "world")
                .reduce((x, y) -> x + " " + y)
                .observeOn(AndroidSchedulers.mainThread())
                //.subscribe(getObserver())
                .subscribe(
                        mTextView::setText,
                        e -> Log.e(TAG, e.getMessage()),
                        () -> Log.i(TAG, "done")
                );
    }

    private MaybeObserver<String> getObserver() {
        return new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                mTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "done");
            }
        };
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            for (String s: strings)
                sb.append(s).append(" ");
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTextView.setText(s);
        }
    }
}
