package com.patrick.rxstudy09;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.patrick.rxstudy09.stock.RetrofitStockServiceFactory;
import com.patrick.rxstudy09.stock.WorldTradingDataService;
import com.patrick.rxstudy09.storio.StockUpdateTable;
import com.patrick.rxstudy09.storio.StorIOFactory;
import com.pushtorefresh.storio3.sqlite.queries.Query;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
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

import static com.patrick.rxstudy09.LogUtil.log;

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

        // 모니터링할 키워드
        final String[] trackingKeyword = { "Google", "Microsoft", "Apple" };

        // 수신할 유형의 필터 정의
        final FilterQuery filterQuery = new FilterQuery()
                .track(trackingKeyword)
                .language("en", "kr");


        // 주식 정보 Observable 과 TwitterStream Observable 을 merge
        mDisposable = Observable.merge(
                Observable.interval(10, TimeUnit.SECONDS)
                        .flatMapMaybe(i -> Observable.fromIterable(symbols)
                                .reduce((symbol1, symbol2) -> symbol1 + "," + symbol2))
                        .flatMapSingle(symbol -> stockService.stockQuery(symbol, key))
                        .flatMap(result -> Observable.fromIterable(result.getData()))
                        .map(StockUpdate::create)
                        // Stock symbol 로 grouping
                        .groupBy(StockUpdate::getStockSymbol)
                        .flatMap(Observable::distinctUntilChanged)
                ,
                observeTwitterStream(configuration, filterQuery)
                        // 수신 속도 조절
                        .sample(3, TimeUnit.SECONDS)
                        .map(StockUpdate::create)
                        .flatMapMaybe(update -> Observable.fromArray(trackingKeyword)
                                .filter(keyword -> update.getTwitterStatus()
                                        .toLowerCase().contains(keyword.toLowerCase()))
                                .map(keyword -> update)
                                .firstElement()
                        )
                        // if 와 for문으로 표현하면
//                        .filter(stockUpdate -> {
//                            for (String keyword : trackingKeyword) {
//                                if (stockUpdate.getTwitterStatus().contains(keyword)) {
//                                    return true;
//                                }
//                            }
//                            return false;
//                        })
        )
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error ->
                        Toast.makeText(this,
                                "We couldn't reach internet - falling back to local data",
                                Toast.LENGTH_SHORT).show())
                .observeOn(Schedulers.io())
                .doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(
                        StorIOFactory.get(this)
                                // StorIO 에게 SELECT 쿼리 작성을 시작하도록 함
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
                                .asRxSingle()
                                .toObservable()
                                .flatMap(Observable::fromIterable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  {
                    log("New update => " + data.getStockSymbol() + " : " + data.getPrice());
                    mNoDataAvailableView.setVisibility(View.GONE);
                    mStockDataAdapter.add(data);
                    mRecyclerView.smoothScrollToPosition(0);
                }, error -> {
                    log("onError...");
                    if (mStockDataAdapter.getItemCount() == 0)
                        mNoDataAvailableView.setVisibility(View.VISIBLE);
                });


        //map();
        //mapNotRight();
        //flatMap();
        //pair();
        //customClass();
        //zip();
        //combineLatest();
        //groupBy();
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

    // map() 사용 예
    private void map() {
        Observable.just(new Date(1), new Date(2), new Date())
                .map(i -> {
                    i.setTime(i.getTime() + 1);
                    return i;
                }).subscribe(i -> log(i.toString()));

        Observable.just(new Date(1), new Date(2), new Date())
                .map(i -> new Date(i.getTime() + 1))
                .subscribe(i -> log(i.toString()));
    }

    // 잘못된 map의 사용 예
    private void mapNotRight() {
        Observable.just("ID1", "ID2", "ID3")
                .map(id -> Observable.fromCallable(mockHttpRequest(id)))
                .subscribe(e -> log(e.toString()));
    }

    // FlatMap 으로 변환
    private void flatMap() {
        Observable.just("ID1", "ID2")
                .flatMap(id -> Observable.fromCallable(mockHttpRequest((id))))
                .subscribe(value -> log("subscribe-subscribe", value.toString()));
    }

    // 여러 값 반환에 Pair 사용 예
    private void pair() {
        Observable.just("UserID1", "UserID2", "UserID3")
                .map(id -> Pair.create(id, id + "-access-token"))
                .subscribe(pair -> log("subscribe-subscribe", pair.second));
    }

    // 커스텀 클래스 만들어 복합 객체 전달
    private void customClass() {
        class User {
            String id;
            public User(String id) {
                this.id = id;
            }
        }

        class UserCredentials {
            final User user;
            final String accessToken;
            public UserCredentials(User user, String accessToken) {
                this.user = user;
                this.accessToken = accessToken;
            }
        }

        Observable.just(new User("1"), new User("2"), new User("3"))
                .map(user -> new UserCredentials(user, "-accessToken"))
                .subscribe(credentials -> log("custom", credentials.user.id + " : " + credentials.accessToken));
    }

    // 결합 zip() 함수 사용 예
    private void zip() {
        Observable.zip(
                Observable.just("One", "Two", "Three")
                    .doOnTerminate(() -> log("just", "terminate"))
                    .doOnDispose(() -> log("just", "dispose")),
                Observable.interval(1, TimeUnit.SECONDS).take(5)
                    .doOnDispose(() -> log("interval", "dispose"))
                    .doOnTerminate(() -> log("interval", "terminate")),
                (number, interval) -> number + "-" + interval)
                .doOnDispose(() -> log("zip", "dispose"))
                .doOnTerminate(() -> log("zip", "terminate"))
                .subscribe(LogUtil::log);
    }

    // combineLatest() 사용 예
    private void combineLatest() {
//        Observable.combineLatest(
//                Observable.just("One", "Two", "Three"),
//                Observable.interval(1, TimeUnit.SECONDS).take(5),
//                (number, interval) -> number + "-" + interval)
//                .subscribe(i -> log("subscribe", i));

        Observable.combineLatest(
                Observable.interval(500, TimeUnit.MILLISECONDS).take(5),
                Observable.interval(1, TimeUnit.SECONDS).take(5),
                (number, interval) -> number + "-" + interval)
                .subscribe(i -> log("subscribe", i));
    }

    // groupBy() 사용 예
    private void groupBy() {
        Observable.just(
                new StockUpdate("APPL", BigDecimal.ONE, new Date(), ""),
                new StockUpdate("APPL", BigDecimal.ONE, new Date(), ""),
                new StockUpdate("GOOG", BigDecimal.ONE, new Date(), ""),
                new StockUpdate("APPL", BigDecimal.ONE, new Date(), "")
        )
                .groupBy(StockUpdate::getStockSymbol)
                .flatMapSingle(groupedObservable -> groupedObservable.count())
                .subscribe(i -> log("groupBy", i.toString()));
    }

    private Callable<Date> mockHttpRequest(String id) { return Date::new; }
}
