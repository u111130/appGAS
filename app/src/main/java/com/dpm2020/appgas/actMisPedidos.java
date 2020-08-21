package com.dpm2020.appgas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class actMisPedidos extends AppCompatActivity {

    ListView lstMisPedidos;
    String[] fromMP = new String[] {"Pedido","Fecha","Direccion","Estado","Despachador","SubTotal","IGV","Total","Producto","Cantidad","TotalLinea"};
    int[] toMP= new int[] {R.id.txtPedidoMPed, R.id.txtFechaMPed, R.id.txtDireccionMPed, R.id.txtEstadoMPed, R.id.txtDespachadorMPed, R.id.txtSubTotalMPed, R.id.txtIGVMPed, R.id.txtTotalMPed, R.id.txtProductoMPed, R.id.txtCantidadMPed, R.id.txtTotalLineaMPed};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_mis_pedidos);
        asignarReferencias();
    }
    private void asignarReferencias() {

        ArrayList<String[]> lista = new ArrayList<>();
        String[] pedido1 = {"202034108","18/08/2020 15:30","Calle ABC #222 dpto 101 San Isidro","Despachando","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","2"};
        String[] pedido2 = {"202012603","18/08/2020 08:20","Av La Paz 888 dpto 101 Miraflores","Delivery","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","2"};
        String[] pedido3 = {"202010322","15/06/2020 15:30","Calle ABC #222 dpto 101 San Isidro","Entregado","Luis","25.42","4.58","30.00","Envasado 5kg","1","S/. 20.00","1"};

        lista.add(pedido1);
        lista.add(pedido2);
        lista.add(pedido3);

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