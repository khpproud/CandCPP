package com.patrick.rxstudy03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.patrick.rxstudy03.stock.AlphavantageService;
import com.patrick.rxstudy03.stock.RetrofitStockServiceFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        AlphavantageService stockService = new RetrofitStockServiceFactory().create();

        // 쿼리의 파라미터 정의
        List<String> symbols = Arrays.asList("MSFT", "AAPL", "GOOGL");
        String key = BuildConfig.API_KEY;

        // 서비스 쿼리를 구독
        Observable<String> observable = Observable.fromIterable(symbols);
        Disposable disposable = observable.flatMap(
                symbol -> stockService.stockQuery(symbol, key).toObservable())
                .map(r -> StockUpdate.create(r.getQuote()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  {
                    log(data.getStockSymbol() + " : " + data.getPrice());
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


    private void log(String stage, String item) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName() + " : " + item);
    }

    private void log(String stage) {
        Log.d(TAG, stage + " : " + Thread.currentThread().getName());
    }

    private void log(Throwable e) { Log.e(TAG, "Error : " + e.getMessage()); }
}
