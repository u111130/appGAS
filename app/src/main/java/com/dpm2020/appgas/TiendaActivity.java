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
    String direcciones[] = {"Jr San Martin 345 Miraflores", "Calle 4 156, San Isidro"};

    ListView lstProductos;
    String[] from = new String[] {"Producto","Precio","Descripcion"};
    int[] to= new int[] {R.id.txtProducto, R.id.txtPrecio, R.id.txtDescripcion};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TiendaActivity activity = this;
        services = new TiendaService(activity);

        setContentView(R.layout.activity_tienda);


        final TextView title = findViewById(R.id.textUsuarioTitulo);
        title.setText("Hola "+ mTuGasPreference.getUserName());
        services.getProducts();
    }

    public void asignarReferencias(JSONArray products) {
        // TODO: GUARDAR PRODUCTOS EN BASE DE DATOS LOCAL

        spDirecciones = findViewById(R.id.spDireccionPed);
        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_activated_1, direcciones);
        spDirecciones.setAdapter(adapter);



        ArrayList<HashMap<String,String>> productos = new ArrayList<>();
        for (int i=0; i<products.length(); i++)
        {
            HashMap<String,String> datosProducto = new HashMap<>();
            try {
                JSONObject product = products.getJSONObject(i);

                datosProducto.put("Producto", product.getString("name"));
                datosProducto.put("Precio", product.getString("price"));
                datosProducto.put("Descripcion", product.getString("description"));
                datosProducto.put("Id", product.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            productos.add(datosProducto);
        }

        lstProductos = findViewById((R.id.lstMisPedidos));
        SimpleAdapter adapter2 = new SimpleAdapter(this, productos, R.layout.tienda_fila,from, to);
        lstProductos.setAdapter(adapter2);

    }

    public void AgregarCarrito (View view){
        Toast.makeText(this, "agregando...", Toast.LENGTH_LONG).show();
        // TODO: agregar el producto al carrito
    }
}