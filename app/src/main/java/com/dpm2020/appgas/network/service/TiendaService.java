package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dpm2020.appgas.TiendaActivity;
import com.dpm2020.appgas.network.Routes;
import com.dpm2020.appgas.ui.login.LoginActivity;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class TiendaService extends BaseService {
    private TiendaActivity activity;

    public TiendaService(TiendaActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (TiendaActivity) activity;
    }

    public void getProducts () {
        activity.showLoading();
        final String token = mTuGasPreference.getToken();

        apiClient.addToRequestQueue(new JsonArrayRequest(Request.Method.GET, Routes.URL_PRODUCTS_LIST, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                activity.hideLoading();
                Log.i("TUGAS", response.toString());
                activity.asignarReferencias(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.hideLoading();
                NetworkResponse networkResponse =  error.networkResponse;

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

    public void logout(){
        showMessage("Sesión expirada.");
        activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
    }

}
