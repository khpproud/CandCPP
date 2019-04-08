package com.patrick.rxstudy01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.hello_world_salute)
    TextView mHelloText;
    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private StockDataAdapter mStockDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(mHelloText::setText);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mStockDataAdapter = new StockDataAdapter();
        mRecyclerView.setAdapter(mStockDataAdapter);

        Observable.just(
                new StockUpdate("GOOGLE", new BigDecimal(12.43), new Date()),
                new StockUpdate("APPLE", new BigDecimal(635.1), new Date()),
                new StockUpdate("TWTR", new BigDecimal(1.43), new Date()))
                .subscribe(stockUpdate -> {
                    Log.d(TAG, "New update " + stockUpdate.getStockSymbol());
                    mStockDataAdapter.add(stockUpdate);
                });

        //demo();
        //demo2();
        //demo3();
        //demo4();
        //demo5();
        //latest();
        //debounce();
        //buffer();
        //completable();
        //single();
        maybe();
    }

    public void demo() {
        // Java 8 이전
        Observable.just("1")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + "mapped";
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just("flat-" + s);
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "on next " + s);
                    }
                })
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MainActivity.this, "Hello~~~ RxAndroid... " + s,
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "Error!!!");
                    }
                });

        // Lambda로 변환
        Observable.just("1")
                .map(s -> s + "mapped")
                .flatMap(s -> Observable.just("flat-" + s))
                .doOnNext(s -> Log.d(TAG, "onNext " + s))
                .subscribe(s -> Toast.makeText(MainActivity.this,
                        "Hello~~~ RxAndroid Lambda... " + s, Toast.LENGTH_SHORT).show(),
                        throwable -> Log.d(TAG, "Error!!!"));

    }

    // Obsavable의 흐름 조사
    private void demo2() {
        Observable.just("One", "Two")
                //.subscribeOn(Schedulers.io())
                .doOnSubscribe(s -> log("doOnSubscribe()"))
                .doOnDispose(() -> log("doOnDispose()"))
                .doOnNext(s -> log("doOnNext()", s))
                .doOnEach(s -> log("doOnEach()"))
                .doOnComplete(() -> log("doOnComplete()"))
                .doOnTerminate(() -> log("doOnTerminate()"))
                .doFinally(() -> log("doFinally()"))
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> log("subscribe()", s));
    }

    // BackPressure 예
    private void demo3() {
        PublishSubject<Integer> observable = PublishSubject.create();

        observable.range(1, 1_000_000)
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);
    }

    // Flowable로 Observable 변환
    private void demo4() {
        Flowable.range(1, 1_000_000)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);

    }

    // 아이템 버리기
    private void demo5() {
        Flowable.range(1, 1_000_000)
                .sample(5, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);
    }

    // 마지막 값만 유지하기
    private void latest() {
        Flowable.range(1, 1_000_000)
                .onBackpressureLatest()
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);
    }

    // 마지막 값만 주기적으로 가져오기 debounce()
    private void debounce() {
        Flowable.range(1, 1_000_000)
                .debounce(10, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);
    }

    // buffer 사용
    private void buffer() {
        Flowable.range(1, 1_000_000)
                .onBackpressureBuffer(10)
                .observeOn(Schedulers.computation())
                .subscribe(v -> log(TAG, v.toString()), this::log);
    }

    // Completable 사용 예
    private void completable() {
        Completable.fromAction(() -> {
            log("Let's do something!!!");
        }).subscribe(() -> log("Finished"), this::log);
    }

    // Single 시용 예
    private void single() {
        Single.just("Item")
                .subscribe(this::log, this::log);
    }

    // Maybe 사용 예
    private void maybe() {
        Maybe.just("Item")
                .subscribe(s -> log("success : " + s),
                        this::log,
                        () -> log("onComplete"));
    }

    private void log(String stage, String item) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    private void log(String stage) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName());
    }

    private void log(Throwable e) { Log.e(TAG, "Error : " + e.getMessage()); }
}
