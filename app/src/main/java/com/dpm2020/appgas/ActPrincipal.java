package com.dpm2020.appgas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_principal);
    }

    public void abrir1(View view){
        Intent formulario = new Intent(this,MainActivity.class);
        startActivity(formulario);
    }

    public void abrir2(View view){
        Intent pepito = new Intent(this, ActRadioCheck.class);
        startActivity(pepito);
    }

    public void abrir3(View view){
        Intent intent = new Intent(this, ActListView.class);
        startActivity(intent);
    }
}