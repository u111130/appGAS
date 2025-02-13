package com.dpm2020.appgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dpm2020.appgas.network.service.MisPedidosService;
import com.dpm2020.appgas.network.service.RegistroService;
import com.dpm2020.appgas.network.service.TiendaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class actMisPedidos extends BaseActivity {

    actMisPedidos activity;
    MisPedidosService services;
    ListView lstMisPedidos;
    String[] fromMP = new String[] {"Pedido","Fecha","Direccion","Estado","Despachador","SubTotal","IGV","Total","Producto","Cantidad","TotalLinea"};
    int[] toMP= new int[] {R.id.txtPedidoMPed, R.id.txtFechaMPed, R.id.txtDireccionMPed, R.id.txtEstadoMPed, R.id.txtDespachadorMPed, R.id.txtSubTotalMPed, R.id.txtIGVMPed, R.id.txtTotalMPed, R.id.txtProductoMPed, R.id.txtCantidadMPed, R.id.txtTotalLineaMPed};

    // MENU
    Button btnCarrito;
    Button btnTienda;
    Button btnMisPedidos;
    Button btnConfig;
    TextView tvCantidad;
    // END MENU

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final actMisPedidos activity = this;
        services = new MisPedidosService(activity);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_mis_pedidos);
        //asignarReferencias();

        // MENU
        tvCantidad = findViewById(R.id.tvCantidad);
        btnTienda = findViewById(R.id.button3);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnMisPedidos = findViewById(R.id.button8);
        btnConfig = findViewById(R.id.button7);

        btnTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), TiendaActivity.class));
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), PedidoActivity.class));
            }
        });

        btnMisPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), actMisPedidos.class));
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mTuGasPreference.clear();
                startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
            }
        });
        this.updateCart();
        tvCantidad.setText(String.valueOf(this.order.getDetails().size()));
        // END MENU

        services.getPedidos();
    }

    /*
    public void setOrders(JSONArray data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        Log.i("TuGAS", json);
    }
     */

    public void setOrders(JSONArray data)  {

        ArrayList<String[]> lista = new ArrayList<>();
        /*
        String[] pedido1 = {"202034108","18/08/2020 15:30","Calle ABC #222 dpto 101 San Isidro","Despachando","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","2"};
        String[] pedido2 = {"202012603","18/08/2020 08:20","Av La Paz 888 dpto 101 Miraflores","Delivery","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","2"};
        String[] pedido3 = {"202010322","15/06/2020 15:30","Calle ABC #222 dpto 101 San Isidro","Entregado","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","1"};

         */

        for (int i=0; i < data.length(); i++) {
            try {
                JSONObject obj = data.getJSONObject(i);
                String code = String.valueOf(obj.get("when")) + "-"+ String.valueOf(obj.get("code"));
                String[] pedido = {code,"18/08/2020 15:30","Calle ABC #222 dpto 101 San Isidro","Despachando","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","2"};
                lista.add(pedido);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayList<HashMap<String,String>> mispedidos = new ArrayList<>();
        for (String[] pedido : lista) {
            HashMap<String,String> datosPedido = new HashMap<>();
            datosPedido.put("Pedido",pedido[0]);
            datosPedido.put("Fecha",pedido[1]);
            datosPedido.put("Direccion",pedido[2]);
            datosPedido.put("Estado",pedido[3]);
            datosPedido.put("Despachador",pedido[4]);
            datosPedido.put("SubTotal",pedido[5]);
            datosPedido.put("IGV",pedido[6]);
            datosPedido.put("Total",pedido[7]);
            datosPedido.put("Producto",pedido[8]);
            datosPedido.put("Cantidad",pedido[9]);
            datosPedido.put("TotalLinea",pedido[10]);
            datosPedido.put("Id",pedido[11]);

            mispedidos.add(datosPedido);
        }

        lstMisPedidos= findViewById((R.id.lstMisPedidos));
        SimpleAdapter adapter3 = new SimpleAdapter(this, mispedidos, R.layout.mispedidos_fila,fromMP, toMP);
        lstMisPedidos.setAdapter(adapter3);

    }
}