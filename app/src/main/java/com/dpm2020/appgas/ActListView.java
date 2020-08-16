package com.dpm2020.appgas;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lstPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list_view);
        asignarReferencias();
    }

    private void asignarReferencias() {
        lstPais = findViewById(R.id.lstPais);
        lstPais.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String nombre;
        nombre = ((TextView)view).getText().toString();
        Toast.makeText(this, "Pais seleccionado: "+nombre+
                            "\nPosicion: "+i, Toast.LENGTH_LONG).show();
    }
}