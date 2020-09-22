package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dpm2020.appgas.LoginActivity;
import com.dpm2020.appgas.TiendaActivity;
import com.dpm2020.appgas.actMisPedidos;
import com.dpm2020.appgas.network.Routes;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MisPedidosService extends BaseService {
    private actMisPedidos activity;

    public MisPedidosService(actMisPedidos activity) {
        super(activity.getApplicationContext());
        this.activity = (actMisPedidos) activity;
    }

    public void getPedidos () {
        showLoading();
        final String token = mTuGasPreference.getToken();

        apiClient.addToRequestQueue(new JsonArrayRequest(Request.Method.GET, Routes.URL_ORDER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideLoading();
                Log.i("TUGAS ADDRESS", response.toString());
                activity.setOrders(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
                NetworkResponse networkResponse =  error.networkResponse;
                Log.i("TUGAS ERROR", networkResponse.toString());

                if (networkResponse.statusCode == 401) {
                    logout();
                } else {
                    showMessage(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "JWT "+ token);
                return params;
            }
        });
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
