package com.patrick.rxstudy09.stock;

import com.patrick.rxstudy09.stock.jsonv2.StockResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * World Trading Data API 요청 인터페이스
 */
public interface WorldTradingDataService {
    @GET("/api/v1/stock?sort_by=list_order")
    Single<StockResult> stockQuery(
            @Query("symbol") String symbol,
            @Query("api_token") String key
    );
}
