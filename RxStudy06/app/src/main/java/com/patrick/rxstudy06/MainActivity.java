package com.patrick.rxstudy06;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.rxstudy06.stock.RetrofitStockServiceFactory;
import com.patrick.rxstudy06.stock.WorldTradingDataService;
import com.patrick.rxstudy06.storio.StockUpdateTable;
import com.patrick.rxstudy06.storio.StorIOFactory;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.patrick.rxstudy06.LogUtil.log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.hello_world_salute)
    TextView mHelloText;
    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.no_data_available)
    TextView mNoDataAvailableView;

    private LinearLayoutManager mLayoutManager;
    private StockDataAdapter mStockDataAdapter;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        Observable.just("Hello! Please use this app responsibly!")
                .subscribe(mHelloText::setText);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mStockDataAdapter = new StockDataAdapter();
        mRecyclerView.setAdapter(mStockDataAdapter);

        // Retrofit 서비스의 인스턴스 생성
        WorldTradingDataService stockService = new RetrofitStockServiceFactory().create();

        // 쿼리의 파라미터 정의
        List<String> symbols = Arrays.asList("MSFT", "AAPL", "GOOGL");
        String key = BuildConfig.API_KEY;

        // 서비스 쿼리를 구독
        mDisposable = Observable.interval(0, 20, TimeUnit.SECONDS)
                .flatMap(i -> Observable.<String>error(new RuntimeException("Oops")))
                .subscribeOn(Schedulers.io())
                // Toast 를 띄우기 위해 Android main 스레드로 흐름 이동
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    log("doOnError()", "error");
                    Toast.makeText(MainActivity.this,
                            "We couldn't reach internet - falling back to local data",
                            Toast.LENGTH_SHORT).show();
                })
                .observeOn(Schedulers.io())
                .flatMap(i -> Observable.fromIterable(symbols)
                    .reduce((symbol1, symbol2) -> symbol1 + "," + symbol2).toObservable())
                .flatMap(symbol -> stockService.stockQuery(symbol, key).toObservable())
                .flatMap(r -> Observable.fromIterable(r.getData()))
                .map(StockUpdate::create)
                .doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(
                        StorIOFactory.get(this)
                        // StorIO 에게 SELECT 쿼리 작성을 시작하도록 지시
                        .get()
                        // 반환될 객체 유형 지정
                        .listOfObjects(StockUpdate.class)
                        // SELECT 쿼리를 만듬
                        .withQuery(Query.builder()
                                // 테이블 지정
                                .table(StockUpdateTable.TABLE)
                                // 날짜의 내림차순으로 정렬
                                .orderBy("date DESC")
                                .limit(10)
                                .build())
                         // 쿼리 구성이 완료 됐음을 알림
                        .prepare()
                        .asRxFlowable(BackpressureStrategy.LATEST)
                        .take(1)
                        .toObservable()
                        .flatMap(Observable::fromIterable)
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  {
                    log("New update => " + data.getStockSymbol() + " : " + data.getPrice());
                    mNoDataAvailableView.setVisibility(View.GONE);
                    mStockDataAdapter.add(data);
                }, error -> {
                    if (mStockDataAdapter.getItemCount() == 0)
                        mNoDataAvailableView.setVisibility(View.VISIBLE);
                });

//        Observable.just(
//                new StockUpdate("GOOGLE", new BigDecimal(12.43), new Date()),
//                new StockUpdate("APPLE", new BigDecimal(635.1), new Date()),
//                new StockUpdate("TWTR", new BigDecimal(1.43), new Date()))
//                .subscribe(stockUpdate -> {
//                    Log.d(TAG, "New update " + stockUpdate.getStockSymbol());
//                    mStockDataAdapter.add(stockUpdate);
//                });

        //demo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    // DB에 Stock data 를 저장
    private void saveStockUpdate(StockUpdate stockUpdate) {
        log("saveStockUpdate()", stockUpdate.getStockSymbol());
        StorIOFactory.get(this)
                .put()
                .object(stockUpdate)
                .prepare()
                .asRxSingle()
                .subscribe();
    }

    // 테이블 삭제 demo
    private void demo2() {
        StockUpdate stockUpdate = null;
        StorIOFactory.get(this)
                .delete()
                .object(stockUpdate)
                .prepare()
                .asRxCompletable()
                .subscribe();
    }

    // error 처리 demo
    private void demo() {
        Observable.<String>error(new Error("Crash!!!"))
                .doOnError(ErrorHandler.get())
                .subscribe(item -> {
                    log("subscribe()", item);
                }, (ErrorHandler.get()));
    }
}
