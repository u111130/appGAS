package com.dpm2020.appgas.network.service;

import android.content.Context;
import android.widget.Toast;

import com.dpm2020.appgas.data.TuGasPreference;
import com.dpm2020.appgas.network.ApiSingleton;

public class BaseService {
    protected TuGasPreference mTuGasPreference;
    protected ApiSingleton apiClient;
    protected Context context;
    public int start = 0;
    public int end = 0;

    public BaseService(Context context) {
        this.context = context;
        this.mTuGasPreference = new TuGasPreference(context);
        apiClient = apiClient.getInstance(context);
    }

    public void showMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void vShowLoading() {
        this.start += 1;
    }

    protected void vHideLoading() {
        this.end += 1;
    }
}
