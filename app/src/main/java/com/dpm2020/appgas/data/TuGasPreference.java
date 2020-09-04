package com.dpm2020.appgas.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.dpm2020.appgas.R;

import static android.content.Context.MODE_PRIVATE;

public class TuGasPreference  {
    private final Context goContext;
    private static final String SHARED_TOKEN = "TOKEN";
    private static final String SHARED_CURRENT_USER = "USER";

    public TuGasPreference(Context poContext) {
        this.goContext = poContext;
    }


    public Context getContext() {
        return this.goContext;
    }

    public String getToken() {
        return getString(SHARED_TOKEN);
    }

    public void setToken(String token) {
        putString(SHARED_TOKEN, token);
    }

    /*
    public void setUser(User user) {
        putString(SHARED_CURRENT_USER, JsonUtil.getStringToJson(user));
    }

    public User getUser() {
        String json = getString(SHARED_CURRENT_USER);
        if (json.isEmpty()) return null;
        return JsonUtil.getJsonToString(json, User.class);
    }
    */

    public void putString(String psKey, String psValue) {
        SharedPreferences sharedPref = getContext().getSharedPreferences(goContext.getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(psKey, psValue);
        editor.apply();
    }

    public String getString(String psKey) {
        SharedPreferences sharedPref = getContext().getSharedPreferences(goContext.getString(R.string.app_name), MODE_PRIVATE);
        return sharedPref.getString(psKey, "");
    }

    public void putLong(String psKey, long psValue) {
        SharedPreferences sharedPref = getContext().getSharedPreferences(goContext.getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(psKey, psValue);
        editor.apply();
    }

    public long getLong(String psKey) {
        SharedPreferences sharedPref = getContext().getSharedPreferences(goContext.getString(R.string.app_name), MODE_PRIVATE);
        return sharedPref.getLong(psKey, -1);
    }
}
