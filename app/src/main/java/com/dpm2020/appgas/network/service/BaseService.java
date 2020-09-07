package com.dpm2020.appgas.network.service;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.dpm2020.appgas.data.TuGasPreference;
import com.dpm2020.appgas.network.ApiSingleton;
import com.dpm2020.appgas.ui.login.LoginActivity;

public class BaseService {
    protected TuGasPreference mTuGasPreference;
    protected ApiSingleton apiClient;
    protected Context context;

    public BaseService(Context context) {
        this.context = context;
        this.mTuGasPreference = new TuGasPreference(context);
        apiClient = apiClient.getInstance(context);
    }

    public void showMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
