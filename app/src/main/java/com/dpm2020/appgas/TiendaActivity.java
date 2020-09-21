package com.dpm2020.appgas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dpm2020.appgas.data.Order;
import com.dpm2020.appgas.network.service.TiendaService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TiendaActivity extends BaseActivity {
    TiendaActivity activity;
    TiendaService services;

    Spinner spDirecciones;
    ListView lstProductos;


    //Enrique
    Order order = new Order();
    ArrayList<String> direccionID = new ArrayList<String>();
    ArrayList<String> productID = new ArrayList<String>();
    Button btnCarrito;
    TextView tvCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TiendaActivity activity = this;
        services = new TiendaService(activity);

        setContentView(R.layout.activity_tienda);

        spDirecciones = findViewById(R.id.spDireccionPed);
        lstProductos = findViewById((R.id.lstMisPedidos));
        tvCantidad = findViewById(R.id.tvCantidad);

        final TextView title = findViewById(R.id.textUsuarioTitulo);
        title.setText(getSaludo());


        //Enrique
        btnCarrito = findViewById(R.id.btnCarrito);

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                IrCarrito();

            }
        });;


        getInfo();
    }

    public void IrCarrito(){
        Gson gson = new Gson();
        Intent intent=new Intent(this.getApplicationContext(), PedidoActivity.class);

        order.setAddress_id(direccionID.get(spDirecciones.getSelectedItemPosition()));
        int i = order.getDetails().size();

        intent.putExtra("lineas", String.valueOf(i));
        intent.putExtra("order", gson.toJson(order));

        startActivity(intent);
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
                direccionID.add(i, item.getString("id"));
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
                //Enrique
                productID.add(item.getString("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String[] from = new String[] {"Producto","Precio","Descripcion"};
        int[] to = new int[] {R.id.txtProducto, R.id.txtPrecio, R.id.txtDescripcion};

        // Enrique
        SimpleAdapter adapter2 = new SimpleAdapter(this, result, R.layout.tienda_fila,from, to)
        {
            @Override
            public View getView (final int position, View convertView, ViewGroup parent)
            {
                final View v = super.getView(position, convertView, parent);

                Button btn =(Button) v.findViewById(R.id.btnAgregar);
                final TextView prod = v.findViewById(R.id.txtProducto);
                final TextView prec = v.findViewById(R.id.txtPrecio);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Order.Details details = new Order.Details(productID.get(position),
                                                prod.getText().toString(),
                                                1, Double.valueOf(prec.getText().toString()),
                                                Double.valueOf(prec.getText().toString()));

                        order.AdicionaDet(details);
                        tvCantidad.setText(String.valueOf(order.getDetails().size()));

                    }
                });


                return v;
            }
        }
                ;
        lstProductos.setAdapter(adapter2);
    }

    //Enrique
    public void AgregarCarrito (View view){
        Toast.makeText(this, "agregando...", Toast.LENGTH_LONG).show();
        // TODO: agregar el producto al carrito
        // Enrique : Se controla por evento en el botón

    }


}