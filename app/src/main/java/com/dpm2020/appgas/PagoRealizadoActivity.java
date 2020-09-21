package com.dpm2020.appgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PagoRealizadoActivity extends BaseActivity {

    PagoRealizadoActivity activity;
    Button btnIrPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pago_realizado);
        final TextView title = findViewById(R.id.tvUsuario);
        title.setText(getSaludo());


        btnIrPedidos = findViewById(R.id.btnIrPedidos);

        btnIrPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), actMisPedidos.class));
            }
        });


    }

}