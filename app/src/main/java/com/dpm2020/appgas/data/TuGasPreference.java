package com.dpm2020.appgas.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.dpm2020.appgas.R;

import static android.content.Context.MODE_PRIVATE;

public class TuGasPreference  {
    private final Context mContext;
    private static final String SHARED_ONBOARDING = "ONBOARDING";
    private static final String SHARED_TOKEN = "TOKEN";
    private static final String SHARED_CURRENT_USER = "USER";
    private static final String SHARED_CURRENT_USER_NAME = "USER_NAME";
    private static final String SHARED_CURRENT_USER_ID = "USER_NAME_ID";


    public TuGasPreference(Context context) {
        this.mContext = context;
    }

    public SharedPreferences getSharedPreferences() {
        return this.mContext.getSharedPreferences(mContext.getString(R.string.app_name), MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditoSharedPreference() {
        return getSharedPreferences().edit();
    }

    public String getToken() {
        return getString(SHARED_TOKEN);
    }

    public void setToken(String value) {
        putString(SHARED_TOKEN, value);
    }

    public String getUserId() {
        return getString(SHARED_CURRENT_USER_ID);
    }

    public void setUserId(String value) {
        putString(SHARED_CURRENT_USER_ID, value);
    }

    public String getUserName() {
        return getString(SHARED_CURRENT_USER_NAME);
    }

    public void setUserName(String value) {
        putString(SHARED_CURRENT_USER_NAME, value);
    }

    public Boolean getOnBoarding() {
        return getBoolean(SHARED_ONBOARDING);
    }

    public void setOnBoarding(Boolean value) {
        putBoolean(SHARED_ONBOARDING, value);
    }

    public String getString(String key) {
        SharedPreferences sharedPref = getSharedPreferences();
        return sharedPref.getString(key, "");
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = getEditoSharedPreference();
        editor.putString(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        SharedPreferences sharedPref = getSharedPreferences();
        return sharedPref.getBoolean(key, false);
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = getEditoSharedPreference();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public long getLong(String psKey) {
        SharedPreferences sharedPref = getSharedPreferences();
        return sharedPref.getLong(psKey, -1);
    }

    public void putLong(String psKey, long psValue) {
        SharedPreferences.Editor editor = getEditoSharedPreference();
        editor.putLong(psKey, psValue);
        editor.apply();
    }
}
