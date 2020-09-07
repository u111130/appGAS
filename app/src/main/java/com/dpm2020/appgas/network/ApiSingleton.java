package com.dpm2020.appgas.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class ApiSingleton {
    private static ApiSingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private ApiSingleton(Context context) {
        ApiSingleton.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized ApiSingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new ApiSingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
}
