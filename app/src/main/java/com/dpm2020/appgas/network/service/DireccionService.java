package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.dpm2020.appgas.DireccionActivity;
import com.dpm2020.appgas.LoginActivity;
import com.dpm2020.appgas.network.Routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DireccionService extends BaseService {
    private DireccionActivity activity;


    public DireccionService(DireccionActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (DireccionActivity) activity;
    }


    public void registrar(String nombre, String interior, double lat, double lon) {
        showLoading();

        final String token = mTuGasPreference.getToken();

        JSONObject json = new JSONObject();
        try{
            json.put("name", nombre);
            json.put("inside", interior);
            json.put("latitude", lat);
            json.put("longitude", lon);
        }catch (JSONException e){
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_REGISTER_ADDRESS, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoading();
                Log.i("TUGAS", response.toString());
                showMessage("Se registro correctamente");
                //activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TUGAS", error.getMessage());
                hideLoading();
                showMessage("No se pudo registrar la dirección, inténtelo nuevamente");
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
            //activity.hideLoading();
        }
    }

}
