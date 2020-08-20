package com.dpm2020.appgas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class actPedido extends AppCompatActivity {


    Spinner spDireccionesPed;
    String direcciones[] = {"Jr San Martin 345 Miraflores", "Calle 4 156, San Isidro"};

    ListView lstPedido;
    String[] from = new String[] {"Producto","Precio","Cantidad","Total"};
    int[] to= new int[] {R.id.txtProductoPed, R.id.txtPrecioPed, R.id.txtCantidadPed, R.id.txtTotalPed};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pedido);
        asignarReferencias();
    }

    private void asignarReferencias() {

        spDireccionesPed = findViewById(R.id.spDireccionPed);
        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_activated_1, direcciones);
        spDireccionesPed.setAdapter(adapter);



        ArrayList<String[]> lista = new ArrayList<>();
        String[] prod1 = {"Envasado 5kg","S/. 20.00","1","S/. 20.00","1"};
        String[] prod2 = {"Envasado 10kg","S/. 30.00","1","S/. 30.00","1"};

        lista.add(prod1);
        lista.add(prod2);

        ArrayList<HashMap<String,String>> pedidos = new ArrayList<>();
        for (String[] pedido : lista) {
            HashMap<String,String> datosPedido = new HashMap<>();
            datosPedido.put("Producto",pedido[0]);
            datosPedido.put("Precio",pedido[1]);
            datosPedido.put("Cantidad",pedido[2]);
            datosPedido.put("Total",pedido[3]);
            datosPedido.put("Id",pedido[4]);

            pedidos.add(datosPedido);
        }

        lstPedido= findViewById((R.id.lstPedido));
        SimpleAdapter adapter2 = new SimpleAdapter(this, pedidos, R.layout.pedido_fila,from, to);
        lstPedido.setAdapter(adapter2);

    }
}