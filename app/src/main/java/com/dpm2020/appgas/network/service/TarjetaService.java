package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dpm2020.appgas.LoginActivity;
import com.dpm2020.appgas.MetodoPagoActivity;
import com.dpm2020.appgas.RegistroActivity;
import com.dpm2020.appgas.network.Routes;

import org.json.JSONException;
import org.json.JSONObject;

public class TarjetaService extends BaseService {
    private MetodoPagoActivity activity;

    public TarjetaService(MetodoPagoActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (MetodoPagoActivity) activity;
    }


    /*
    {
	"name": "**** **** **** 1234",
	"token": "2883599064508077J"
}
     */

    public String registrar(String nombre, String token2){

        String ret;

        final String token = mTuGasPreference.getToken();

        ret = "";
        showLoading();
        JSONObject json = new JSONObject();
        try{
            json.put("name", nombre);
            json.put("token", token2);
        }catch (JSONException e){
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_CARD, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoading();
                Log.i("TUGAS", response.toString());
                //showMessage("Se registro correctamente");
                //activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TUGAS", error.getMessage());
                hideLoading();
                showMessage("No se pudo registrar la tarjeta, inténtelo nuevamente");
            }
        }));
        return ret;
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

}
