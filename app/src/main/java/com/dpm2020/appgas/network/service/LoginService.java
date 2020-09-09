package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dpm2020.appgas.TiendaActivity;
import com.dpm2020.appgas.network.Routes;
import com.dpm2020.appgas.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginService extends BaseService {
    private LoginActivity activity;

    public LoginService(LoginActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (LoginActivity) activity;
    }

    public void login(String username, String password) {
        showLoading();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_AUTH_LOGIN, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoading();
                Log.i("TUGAS", response.toString());
                try {
                    mTuGasPreference.setToken(response.getString("token"));
                    mTuGasPreference.setUserId(response.getJSONObject("user").getString("name"));
                    mTuGasPreference.setUserName(response.getJSONObject("user").getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                activity.startActivity(new Intent(activity.getApplicationContext(), TiendaActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
                showMessage("Usuario o contraseña incorrectos");
            }
        }));
    }



    public void verify() {
        showLoading();

        final String token = mTuGasPreference.getToken();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_AUTH_VERIFY, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoading();
                Log.i("TUGAS", response.toString());
                try {
                    mTuGasPreference.setToken(response.getString("token"));
                    mTuGasPreference.setUserId(response.getJSONObject("user").getString("name"));
                    mTuGasPreference.setUserName(response.getJSONObject("user").getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                activity.startActivity(new Intent(activity.getApplicationContext(), TiendaActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
                logout();
            }
        }));
    }

    public void showLoading() {
        this.vShowLoading();
        activity.showLoading();
    }

    public void hideLoading() {
        this.vHideLoading();
        if(this.start == this.end) {
            activity.hideLoading();
        }
    }

    public void logout(){
        showMessage("Sesión expirada.");
        mTuGasPreference.clear();
        activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
    }
}
