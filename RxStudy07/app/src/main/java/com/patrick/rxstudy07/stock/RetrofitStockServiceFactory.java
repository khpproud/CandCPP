package com.patrick.rxstudy07.stock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.patrick.rxstudy07.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    // Retrofit + Gson 으로 Date 객체 얻을 시 indicator 포맷 에러(' ') 때문에 추가
    final Gson builder = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonDeserializer) (json, typeOfT, context) ->
            {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
                            .parse(json.getAsString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return "0000-00-00 00:00:00";
            })
            .create();

    // Retrofit 객체 준비
    Retrofit retrofit = new Retrofit.Builder()
            // okHttp Client 지정
            .client(client)
            // RxJava 호환성 Retrofit 라이브러리로 통합
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            // JSON 응답 지원 (Date JsonDeserializer 변환한 Gson builder 추가)
            .addConverterFactory(GsonConverterFactory.create(builder))
            // baseUrl
            .baseUrl(BuildConfig.BASE_URL)
            .build();

    // Retrofit 서비스 객체 생성 후 리턴
    public WorldTradingDataService create() {
        return retrofit.create(WorldTradingDataService.class);
    }
}
