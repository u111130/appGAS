package com.dpm2020.appgas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dpm2020.appgas.data.TuGasPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class actTienda extends AppCompatActivity {
    private final String URL_LIST_PRODUCTS = "https://apigas.komanda.pe/orders/product";
    private TuGasPreference mTuGasPreference;

    public actTienda(Context context) {
        this.mTuGasPreference =new TuGasPreference(context);
    }

    Spinner spDirecciones;
    String direcciones[] = {"Jr San Martin 345 Miraflores", "Calle 4 156, San Isidro"};

    ListView lstProductos;
    String[] from = new String[] {"Producto","Precio","Descripcion"};
    int[] to= new int[] {R.id.txtProducto, R.id.txtPrecio, R.id.txtDescripcion};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tienda);
        asignarReferencias();
    }

    private void wsGetProducts () {
        JSONObject jsonBody = new JSONObject();
        EditText usernameEditText = findViewById(R.id.txtUsername);
        EditText passwordEditText = findViewById(R.id.password);



        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_LIST_PRODUCTS, null, new Response.Listener<JSONArray>() {
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                String token = prefs.getString("token", null);
                Log.i("TUGAS", token);
                header.put("Authorization", "JWT "+ token);
                return header;
            }

            @Override
            public void onResponse(JSONArray response) {
                //loadingHide();
                // JSONObject jsonObject = JSONObject(response);
                Log.i("TUGAS", response.toString());
                /*
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("token", response.getString("token"));
                editor.putString("user", response.getString("user"));
                editor.commit();

                 */
                startActivity(new Intent(getApplicationContext(), actTienda.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //loadingHide();
                Toast.makeText(getApplicationContext(), "Lo sentimos, no se encontro productos", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void asignarReferencias() {

        spDirecciones = findViewById(R.id.spDireccionPed);
        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_activated_1, direcciones);
        spDirecciones.setAdapter(adapter);

        ArrayList<String[]> lista = new ArrayList<>();
        String[] prod1 = {"Envasado 5kg","S/. 20.00","Dirigido a los que gustan del camping y a los que requieren emplear el gas en circunstancias donde, que por su tamaño y capacidad, resulta una gran ventaja.","1"};
        String[] prod2 = {"Envasado 10kg","S/. 30.00","De uso doméstico. Tenemos presentaciones para todo tipo de reguladores.","2"};
        String[] prod3 = {"Envasado 15kg","S/. 40.00","Destinado para una compra reflexiva, pues insume un menor precio por kilo.","3"};

        lista.add(prod1);
        lista.add(prod2);
        lista.add(prod3);

        ArrayList<HashMap<String,String>> productos = new ArrayList<>();
        for (String[] producto : lista) {
            HashMap<String,String> datosProducto = new HashMap<>();
            datosProducto.put("Producto",producto[0]);
            datosProducto.put("Precio",producto[1]);
            datosProducto.put("Descripcion",producto[2]);
            datosProducto.put("Id",producto[3]);

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