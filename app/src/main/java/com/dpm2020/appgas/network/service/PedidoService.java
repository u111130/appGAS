package com.dpm2020.appgas.network.service;

import android.content.Intent;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dpm2020.appgas.LoginActivity;
import com.dpm2020.appgas.PedidoActivity;
import com.dpm2020.appgas.data.Order;
import com.dpm2020.appgas.network.Routes;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoService extends BaseService {
    private PedidoActivity activity;

    public PedidoService(PedidoActivity activity) {
        super(activity.getApplicationContext());
        this.activity = (PedidoActivity) activity;
    }

    public void getOrder () {
        showLoading();
        final String token = mTuGasPreference.getToken();
        // TODO: crear ruta para leer 1 sola ORDEN

        apiClient.addToRequestQueue(new JsonArrayRequest(Request.Method.GET, Routes.URL_ORDER, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideLoading();
                Log.i("TUGAS PRODUCTS", response.toString());
                activity.setOrder(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
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

    public void registrarOrder(Order order) {
        showLoading();

        final String token = mTuGasPreference.getToken();

        /*
        private String address_id;
        private String payment_method;
        private String card_id;
        private List<Order.Details> details = new ArrayList<Order.Details>();
           public Details(String product_id, String name, int quantity, double price, double linetotal) {
            this.product_id = product_id;
            this.name = name;
            this.quantity = quantity;
            ArrayList<HashMap<String,String>> result = new ArrayList<>();
        */

        List<Order.Details> pedido = new ArrayList<>();
        List<Order.Products> orderdet = new ArrayList<>();

        JSONObject json = new JSONObject();
        try{
            json.put("address_id", order.getAddress_id());
            json.put("payment_method", order.getPayment_method());
            json.put("card_id", order.getCard_id());

            for (Order.Details details : pedido = order.getDetails()) {

                Order.Products prod = new Order.Products();
                prod.setProduct_id(details.getProduct_id());
                prod.setQuantity(details.getQuantity());

                orderdet.add(prod);
            }
            json.put("details", orderdet);
            Gson gson = new Gson();
            String jsonString = gson.toJson(json);
            Log.i("GUARDAR", jsonString);

        }catch (JSONException e){
            e.printStackTrace();
        }

        apiClient.addToRequestQueue(new JsonObjectRequest(Request.Method.POST, Routes.URL_ORDER, json, new Response.Listener<JSONObject>() {
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
                showMessage("No se pudo registrar el pedido, inténtelo nuevamente");
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

    public void updateOrder () {

    }


    public void getAddress () {
        showLoading();
        final String token = mTuGasPreference.getToken();

        apiClient.addToRequestQueue(new JsonArrayRequest(Request.Method.GET, Routes.URL_ADDRESS_LIST, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideLoading();
                Log.i("TUGAS ADDRESS", response.toString());
                activity.setAddress(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
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
