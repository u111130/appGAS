package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dpm2020.appgas.LoginActivity;
import com.dpm2020.appgas.network.Routes;
import com.dpm2020.appgas.RegistroActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroService extends BaseService {
    private  RegistroActivity activity;

    public RegistroService(RegistroActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (RegistroActivity) activity;
    }


    public void registrar(String tipoDoc, String numeroDoc, String nombres, String apellidos, String correo, String telefono, String password){

        showLoading();
        JSONObject json = new JSONObject();
        try{
            json.put("document_type", tipoDoc);
            json.put("document_number", numeroDoc);
            json.put("name", nombres);
            json.put("lastname", apellidos);
            json.put("phone", telefono);
            json.put("email", correo);
            json.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_REGISTER_USER, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoading();
                Log.i("TUGAS", response.toString());
                showMessage("Se registro correctamente");
                activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TUGAS", error.getMessage());
                hideLoading();
                showMessage("No se pudo registrar el usuario, inténtelo nuevamente");
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

}
