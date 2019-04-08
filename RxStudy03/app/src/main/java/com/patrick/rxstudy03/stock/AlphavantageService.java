package com.patrick.rxstudy03.stock;

import com.patrick.rxstudy03.stock.json.StockResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * alphavantage API 요청 인터페이스 정의
 */
public interface AlphavantageService {
    @GET("/query?function=GLOBAL_QUOTE")
    Single<StockResult> stockQuery(
        @Query("symbol") String symbol,
        @Query("apikey") String key
    );
}
