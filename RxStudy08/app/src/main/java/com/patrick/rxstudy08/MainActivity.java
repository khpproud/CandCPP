package com.patrick.rxstudy08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.rxstudy08.stock.RetrofitStockServiceFactory;
import com.patrick.rxstudy08.stock.WorldTradingDataService;
import com.patrick.rxstudy08.storio.StockUpdateTable;
import com.patrick.rxstudy08.storio.StorIOFactory;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import static com.patrick.rxstudy08.LogUtil.log;

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
                    log("onError...");
                    if (mStockDataAdapter.getItemCount() == 0)
                        mNoDataAvailableView.setVisibility(View.VISIBLE);
                });

        //customObservable();
        cleanObservable();
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

    // Observable 로 TwitterStream status 출력
    private Observable<Status> observeTwitterStream(
            Configuration configuration, FilterQuery filterQuery) {
        return Observable.create(emitter -> {
            // TwitterStream 객체 초기화
            final TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

            // Observable 이 제거될 때 TwitterStream 을 제거하기 위한 콜백 추가
            emitter.setCancellable(() -> twitterStream.shutdown());

            StatusListener listener = new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    // Observable 에 상태 업데이트 추가
                    emitter.onNext(status);
                    log(status.getUser().getName() + " : " + status.getText());
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }

                @Override
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) { }

                @Override
                public void onScrubGeo(long userId, long upToStatusId) { }

                @Override
                public void onStallWarning(StallWarning warning) { }

                @Override
                public void onException(Exception ex) {
                    // 예외 처리 추가
                    emitter.onError(ex);
                    log(ex);
                }
            };

            // TwitterStream 에 리스너 추가
            twitterStream.addListener(listener);
            // Status 얻기 위한 필터 추가
            twitterStream.filter(filterQuery);
        });
    }

    // Twitter 설정
    private void twitter() {

        // 트위터 구성(authentication setting)
        final Configuration configuration = new ConfigurationBuilder()
                // 디버깅 설정
                .setDebugEnabled(BuildConfig.DEBUG)
                // key 설정
                .setOAuthConsumerKey(BuildConfig.CONSUMER_KEY)
                .setOAuthConsumerSecret(BuildConfig.CONSUMER_SECRET)
                .setOAuthAccessToken(BuildConfig.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.ACCESS_TOKEN_SECRET)
                .build();

        // Configuration 객체가 사용 가능할 때 상태 업데이트를 모니터링 하고 해당 데이터를 수신
        TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

        // 상태 업데이트를 수신할 리스너
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                log(status.getUser().getName() + " : " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                log(statusDeletionNotice.toString());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) { }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) { }

            @Override
            public void onStallWarning(StallWarning warning) { }

            @Override
            public void onException(Exception ex) {
                log(ex);
            }
        };

        // Twitter Stream 에 연결
        twitterStream.addListener(listener);
        // 연결을 시작하고 업게이트를 수신
        twitterStream.filter(
                // 수신할 유형 필터 정의(영어 트윗만)
                new FilterQuery()
                .track("Google", "Microsoft", "Apple inc")
                .language("en")
        );
    }

    // 커스텀 Observable 예
    private void customObservable() {
        // from Callable
        Observable.fromCallable(() -> longRunningOperation.call())
                .subscribeOn(Schedulers.io())
                .subscribe(item -> log("subscribe()", item.toString()));

        // from Future
//        Observable.fromFuture(new FutureTask<>(() -> longRunningOperation))
//                .subscribeOn(Schedulers.io())
//                .subscribe(item -> log("subscribe()", item.toString()));

        // Emitter Interface 를 통한 Observable 생성 및 제어
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onComplete();
        })
        .subscribe(item -> log("subscribe()", item.toString()));
    }

    private Callable<Integer> longRunningOperation = () -> {
        Thread.sleep(1_000);
        log("longRunningOperation");
        return 0;
    };

    // Observable 사용 후 정리 예
    private void cleanObservable() {
        final Disposable disposable = Observable.create(emitter -> {
            emitter.setCancellable(() -> {
                log("setCancellable()");
                mHelloText.setOnClickListener(null);
            });
            mHelloText.setOnClickListener(v ->  {
                log("setOnClickListener()");
                emitter.onNext(v);
            });
        })
                .doOnComplete(() -> log("onComplete"))
                .doOnDispose(() -> log("onDispose"))
                .subscribe(c -> log("onClick", c.toString()));
        disposable.dispose();
    }

    // MainThreadDisposable 사용 예
    private void mainThreadDisposable() {
        Observable.create(emitter -> {
            emitter.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    mHelloText.setOnClickListener(null);
                }
            });
            mHelloText.setOnClickListener(v -> emitter.onNext(v));
        }).subscribe();
    }
}
