package com.patrick.rxstudy03.stock;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.patrick.rxstudy03.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitStockServiceFactory {
    // 로깅을 담당할 interceptor 정의
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    // OkHtp Client 준비
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    // Retrofit 객체 준비
    Retrofit retrofit = new Retrofit.Builder()
            // okHttp Client 지정
            .client(client)
            // RxJava 호환성 Retrofit 라이브러리로 통합
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // JSON 응답 지원
            .addConverterFactory(GsonConverterFactory.create())
            // baseUrl
            .baseUrl(BuildConfig.BASE_URL)
            .build();

    // Retrofit 서비스 객체 생성 후 리턴
    public AlphavantageService create() {
        return retrofit.create(AlphavantageService.class);
    }
}
