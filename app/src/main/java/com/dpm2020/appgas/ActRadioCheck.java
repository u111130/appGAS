package com.dpm2020.appgas;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActRadioCheck extends AppCompatActivity {

    EditText txtNombres;
    RadioGroup rg;
    CheckBox chCinco, chDiez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_radio_check);
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtNombres = findViewById(R.id.txtNombres);
        rg = findViewById(R.id.rbGrupo);
        chCinco = findViewById(R.id.chCinco);
        chDiez = findViewById(R.id.chDiez);
    }

    public void calcularPrecio(View view){
        int precio;
        String nombre, curso;
        double dcto5 = 0, dcto10 = 0;

        nombre = txtNombres.getText().toString();
        if(rg.getCheckedRadioButtonId() == R.id.rbAndroid){
            precio = 500;
            curso = "Android Studio";
        }else if(rg.getCheckedRadioButtonId() == R.id.rbJava){
            precio = 400;
            curso = "Java";
        }else if(rg.getCheckedRadioButtonId() == R.id.rbVisual){
            precio = 300;
            curso = "Visual Studio - C#";
        }else{
            precio = 200;
            curso = "PHP";
        }

        if(chCinco.isChecked()){
            dcto5 = precio * 0.05;
        }
        if(chDiez.isChecked()){
            dcto10 = precio * 0.10;
        }

        Toast.makeText(this, "Nombres: "+nombre+
                "\nCurso: "+curso+
                "\nPrecio: "+precio+
                "\nDescuento: "+(dcto5+dcto10)+
                "\nMonto a Pagar: "+(precio-dcto5-dcto10), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Hola señorita Zarate", Toast.LENGTH_LONG).show();
    }
}