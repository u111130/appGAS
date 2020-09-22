package com.dpm2020.appgas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.dpm2020.appgas.data.Order;
import com.dpm2020.appgas.network.service.PedidoService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PedidoActivity extends BaseActivity  {
    PedidoActivity activity;
    PedidoService services;

    Spinner spDireccionPed;
    ListView lstMisPedidos;
    TextView txtTotalPed;
    ImageButton btnDirecc;
    Button btnPagar;

    ArrayList<String> direcciones = new ArrayList<String>();
    ArrayList<String> direccionesId = new ArrayList<>();
    List<Order.Details> lista = new ArrayList<>();

    ArrayAdapter adapter;
    SimpleAdapter adapter2;

    //Order order = new Order();

    //Order order;

    // MENU
    Button btnCarrito;
    Button btnTienda;
    Button btnMisPedidos;
    Button btnConfig;
    // END MENU

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PedidoActivity activity = this;
        services = new PedidoService(activity);
        setContentView(R.layout.activity_act_pedido);

        spDireccionPed = findViewById(R.id.spDireccionPed);
        lstMisPedidos = findViewById((R.id.lstMisPedidos));
        txtTotalPed = findViewById(R.id.txtTotalPed);
        btnDirecc = findViewById(R.id.btnDirecc);
        btnPagar = findViewById(R.id.btnPagar);

        btnDirecc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DireccionActivity.class));
            }
        });


        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpago = new Intent(getApplicationContext(), MetodoPagoActivity.class);
                order.setAddress_id(direccionesId.get(spDireccionPed.getSelectedItemPosition()));
                intentpago.putExtra("total", order.calcularTotal());
                saveCart();
                startActivityForResult(intentpago, 1);
            }
        });

        final TextView title = findViewById(R.id.textUsuarioTitulo);
        title.setText(getSaludo());

        //order = new Order();

        /*
        Intent i = getIntent();
        if (i != null) {
            // JSONObject datos = new JSONObject(i.getSerializableExtra("order"));
            //order = (Order) i.getSerializableExtra("order");
            //order = null;
            Gson gson = new Gson();
            int lineas = Integer.valueOf(i.getStringExtra("lineas"));
            //String dato = i.getStringExtra("order");
            if (lineas > 0) {
                //for (int x = 1; x <= lineas; x++) {
                //    Order.Details det1 = new Order.Details();
                //    order.AdicionaDet(det1);
                //}
                //order = gson.fromJson(i.getStringExtra("order"),Order.class);
            }
        }
         */

        // MENU
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
        //tvCantidad.setText(String.valueOf(this.order.getDetails().size()));
        // END MENU


        getInfo();
        showOrder();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                //retorna PaymentMethod y CardID
                String sPMetodo = data.getStringExtra("metodo");
                String sCardID = data.getStringExtra("cardId");
                order.setPayment_method(sPMetodo);
                order.setCard_id(sCardID);



                //TODO: Grabar la Orden

                services.registrarOrder(order);

                startActivity(new Intent(getApplicationContext(), PagoRealizadoActivity.class));
            }
        }
    }

    public void getInfo() {
        services.getAddress();
        //services.getOrder();
    }


    public void setAddress(JSONArray data) {
        // TODO: GUARDAR DIRECCIONES EN BASE DE DATOS LOCAL

        direcciones.clear();
        direccionesId.clear();

        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        for (int i = 0; i < data.length(); i++)
        {

            try {
                JSONObject item = data.getJSONObject(i);

                direcciones.add(i, item.getString("name"));
                direccionesId.add(i, item.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_activated_1, direcciones);
        spDireccionPed.setAdapter(adapter);

        int addressSelected = mTuGasPreference.getInt("direccion");
        if (addressSelected != -1) {
            spDireccionPed.setSelection(addressSelected);
        }
    }

    public void setOrder(JSONArray data){
        int pos = -1;
    }

    public void showOrder() {

        // elige la dirección

        int pos = -1;
        pos = direccionesId.indexOf(order.getAddress_id());
        if (pos > 0) {
            spDireccionPed.setSelection(pos);
        }

        ArrayList<HashMap<String,String>> result = new ArrayList<>();

        for (Order.Details details : lista = order.getDetails()) {

            HashMap<String,String> temp = new HashMap<>();
            temp.put("Producto", details.getName());
            temp.put("Precio", String.valueOf(details.getPrice()));
            temp.put("Cantidad", String.valueOf(details.getQuantity()));
            temp.put("TotalItem", String.valueOf(details.getLinetotal()));

            result.add(temp);
        }

        String[] from = new String[] {"Producto","Precio","Cantidad","TotalItem"};
        int[] to = new int[] {R.id.txtProductoPed, R.id.txtPrecioPed, R.id.txtCantidadPed, R.id.txtTotalMPed};

        final SimpleAdapter adapter2 = new SimpleAdapter(this, result, R.layout.pedido_fila,from, to)

        {

            @Override
            public View getView (final int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                ImageButton bMenos =(ImageButton) v.findViewById(R.id.btnMenosPed);
                ImageButton bMas =(ImageButton) v.findViewById(R.id.btnMasPed);
                final TextView tCant =(TextView) v.findViewById(R.id.txtCantidadPed);
                final TextView tPrec =(TextView) v.findViewById(R.id.txtPrecioPed);
                final TextView tTotal =(TextView) v.findViewById(R.id.txtTotalMPed);

                bMenos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        //int cant = Integer.valueOf(tCant.getText().toString());
                        //double prec = Double.valueOf(tPrec.getText().toString());

                        Order.Details linea = lista.get(position);
                        int cant = linea.getQuantity();
                        double prec = linea.getPrice();

                        if (cant > 1) {
                            cant--;
                            double tot = prec * cant;
                            //tot = Math.round((tot, 2);
                            tCant.setText(String.valueOf(cant));
                            tTotal.setText(String.valueOf(tot));

                            linea.setLinetotal(tot);
                            linea.setQuantity(cant);
                            lista.set(position, linea);

                        } else {
                            lista.remove(linea);
                        }
                        showTotal();
                    }
                });

                bMas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        //int cant = Integer.valueOf(tCant.getText().toString());
                        //double prec = Double.valueOf(tPrec.getText().toString());

                        Order.Details linea = lista.get(position);
                        int cant = linea.getQuantity();
                        double prec = linea.getPrice();

                        cant++;
                        double tot = prec * cant;
                        //tot = Math.round((tot, 2);
                        tCant.setText(String.valueOf(cant));
                        tTotal.setText(String.valueOf(tot));

                        linea.setLinetotal(tot);
                        linea.setQuantity(cant);
                        lista.set(position, linea);
                        showTotal();

                    }
                });

                return v;
            }
        };

        lstMisPedidos.setAdapter(adapter2);

        // muestra el total
        showTotal();

    }

    public void showTotal()
    {
        // muestra el total
        double total = order.calcularTotal();
        txtTotalPed.setText(String.valueOf(total));
    }
}