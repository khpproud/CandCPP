package com.patrick.rxandroidsample.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Volley 라이브러리 초기화
 */
public class LocalVolley {
    private static RequestQueue sRequestQueue;
    private LocalVolley() { }

    public static void init(Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (sRequestQueue != null)
            return sRequestQueue;
        else
            throw new IllegalStateException("Not init...");
    }
}
