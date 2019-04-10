package com.patrick.rxstudy07;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.patrick.rxstudy07.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.patrick.rxstudy07.LogUtil.*;

/**
 * 메모리 누수 테스트용 Activity
 */
public class MockActivity extends AppCompatActivity {
    // 액티비티에 참조를 저장할 전역 리스트
    private static final List<MockActivity> INSTANCE = new ArrayList<>();

    private String[] bigArray = new String[10_000_000];

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mock);
//
//        bigArray[0] = "test";
//        INSTANCE.add(this);
//        Log.i("APP", "Activity Created");
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mock);
//
//        bigArray[0] = "test";
//        getApplication().registerComponentCallbacks(new ComponentCallbacks() {
//            @Override
//            public void onConfigurationChanged(Configuration newConfig) {
//                Log.i("APP", "Some logging");
//            }
//
//            @Override
//            public void onLowMemory() {
//                Log.w("APP", "OOME...");
//            }
//        });
//        Log.i("APP", "Activity Created");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        Observable.interval(0, 2, TimeUnit.SECONDS)
                .take(30)
                .subscribe(i -> log("Instance " + this.toString() + " reporting"));
        Log.i("APP", "Activity Created");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("APP", "Activity Destroyed!!!");
    }
}
