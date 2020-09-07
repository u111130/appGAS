package com.dpm2020.appgas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dpm2020.appgas.network.service.TiendaService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TiendaActivity extends BaseActivity {
    TiendaActivity activity;
    TiendaService services;

    Spinner spDirecciones;
    ListView lstProductos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TiendaActivity activity = this;
        services = new TiendaService(activity);

        setContentView(R.layout.activity_tienda);

        spDirecciones = findViewById(R.id.spDireccionPed);
        lstProductos = findViewById((R.id.lstMisPedidos));

        final TextView title = findViewById(R.id.textUsuarioTitulo);
        title.setText(getSaludo());

        getInfo();
    }

    public void getInfo() {
        services.getAddress();
        services.getProducts();
    }

    public void setAddress(JSONArray data) {
        // TODO: GUARDAR DIRECCIONES EN BASE DE DATOS LOCAL

        ArrayList<String> direcciones = new ArrayList<String>();
        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        for (int i = 0; i < data.length(); i++)
        {

            try {
                JSONObject item = data.getJSONObject(i);
                /*
                HashMap<String,String> temp = new HashMap<>();
                temp.put("id", item.getString("id"));
                temp.put("name", item.getString("name"));
                temp.put("inside", item.getString("inside"));
                temp.put("latitude", item.getString("latitude"));
                temp.put("longitude", item.getString("longitude"));
                // temp.put("member_id", item.getString("member_id"));

                result.add(temp);
                */
                direcciones.add(i, item.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_activated_1, direcciones);
        spDirecciones.setAdapter(adapter);
    }

    public void setProducts(JSONArray data) {
        // TODO: GUARDAR PRODUCTOS EN BASE DE DATOS LOCAL

        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        for (int i = 0; i < data.length(); i++)
        {
            try {
                JSONObject item = data.getJSONObject(i);

                HashMap<String,String> temp = new HashMap<>();
                temp.put("Producto", item.getString("name"));
                temp.put("Precio", item.getString("price"));
                temp.put("Descripcion", item.getString("description"));
                temp.put("Id", item.getString("id"));

                result.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String[] from = new String[] {"Producto","Precio","Descripcion"};
        int[] to = new int[] {R.id.txtProducto, R.id.txtPrecio, R.id.txtDescripcion};

        SimpleAdapter adapter2 = new SimpleAdapter(this, result, R.layout.tienda_fila,from, to);
        lstProductos.setAdapter(adapter2);
    }

    public void AgregarCarrito (View view){
        Toast.makeText(this, "agregando...", Toast.LENGTH_LONG).show();
        // TODO: agregar el producto al carrito
    }
}