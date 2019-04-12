package com.patrick.rxstudy11;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.Arrays;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class Settings {
    private static Settings INSTANCE;
    // 트위터에 모니터링된 키워드를 가져오기 위해
    private Subject<List<String>> keywordsSubject = BehaviorSubject.create();
    // 주식 symbol을 가져오는데 사용
    private Subject<List<String>> symbolSubject = BehaviorSubject.create();

    private Settings(Context context) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        // 설정에 Subject 연결
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        // pref_keywords 속성을 Subject에 연결
        rxPreferences.getString("pref_keywords", "").asObservable()
                .filter(v -> !v.isEmpty())
                .map(value -> {
                    if (value.contains(" "))
                        return value.split(" ");
                    else {
                        return new String[]{ value };
                    }
                })
                .map(Arrays::asList)
                .subscribe(keywordsSubject);

        rxPreferences.getString("pref_symbols", "").asObservable()
                .filter(v -> !v.isEmpty())
                .map(String::toUpperCase)
                .map(value -> {
                    if (value.contains(" "))
                        return value.split(" ");
                    else {
                        return new String[]{ value };
                    }
                })
                .map(Arrays::asList)
                .subscribe(symbolSubject);
    }

    public synchronized static Settings get(Context context) {
        if (INSTANCE != null)
            return INSTANCE;

        INSTANCE = new Settings(context);
        return INSTANCE;
    }

    public Subject<List<String>> getMonitoredKeywords() {
        return keywordsSubject;
    }

    public Subject<List<String>> getMonitoredSymbols() {
        return symbolSubject;
    }
}
