package com.patrick.rxandroidsample.square;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit API를 사용하는 사용자화 Adapter 클래스
 */
public class RestfulAdapter {
    private static final String BASE_URL = "https://api.github.com/";

    // singleton 으로 객체 생성
    private RestfulAdapter() { }
    private static class Singleton {
        private static final RestfulAdapter instance = new RestfulAdapter();
    }

    public static RestfulAdapter getInstance() {
        return Singleton.instance;
    }

    // Retrofit 기본 호출
    public GitHubServiceApi getSimpleApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GitHubServiceApi.class);
    }

    // REST API 스택의 디버깅(OkHttpClient의 로그를 위한 인터셉터 추가)
    public GitHubServiceApi getServiceApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(GitHubServiceApi.class);
    }
}
