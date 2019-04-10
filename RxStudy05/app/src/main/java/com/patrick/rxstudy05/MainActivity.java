package com.patrick.rxstudy05;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.patrick.rxstudy05.stock.RetrofitStockServiceFactory;
import com.patrick.rxstudy05.stock.WorldTradingDataService;
import com.patrick.rxstudy05.storio.StorIOFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.hello_world_salute)
    TextView mHelloText;
    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private StockDataAdapter mStockDataAdapter;
    private Disposable mDisposable;

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

        // Retrofit 서비스의 인스턴스 생성
        WorldTradingDataService stockService = new RetrofitStockServiceFactory().create();

        // 쿼리의 파라미터 정의
        List<String> symbols = Arrays.asList("MSFT", "AAPL", "GOOGL");
        String key = BuildConfig.API_KEY;

        // 서비스 쿼리를 구독
        mDisposable = Observable.interval(0, 20, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(i -> Observable.fromIterable(symbols)
                    .reduce((symbol1, symbol2) -> symbol1 + "," + symbol2).toObservable())
                .flatMap(symbol -> stockService.stockQuery(symbol, key).toObservable())
                .flatMap(r -> Observable.fromIterable(r.getData()))
                .map(StockUpdate::create)
                .doOnNext(this::saveStockUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  {
                    log("New update => " + data.getStockSymbol() + " : " + data.getPrice());
                    mStockDataAdapter.add(data);
                });

//        Observable.just(
//                new StockUpdate("GOOGLE", new BigDecimal(12.43), new Date()),
//                new StockUpdate("APPLE", new BigDecimal(635.1), new Date()),
//                new StockUpdate("TWTR", new BigDecimal(1.43), new Date()))
//                .subscribe(stockUpdate -> {
//                    Log.d(TAG, "New update " + stockUpdate.getStockSymbol());
//                    mStockDataAdapter.add(stockUpdate);
//                });
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

    public static void log(String stage, String item) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    public static void log(String stage) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName());
    }

    public static void log(Throwable e) { Log.e(TAG, "Error : " + e.getMessage()); }

}
