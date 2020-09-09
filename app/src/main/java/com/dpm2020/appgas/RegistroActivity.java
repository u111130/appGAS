package com.dpm2020.appgas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dpm2020.appgas.network.service.LoginService;
import com.dpm2020.appgas.network.service.RegistroService;

import java.util.regex.Pattern;

public class RegistroActivity extends BaseActivity {

    RegistroActivity activyti;
    RegistroService service;

    Spinner spTipoDocumento;
    EditText txtNumeroDocumento, txtNombres, txtApellidos, txtCorreo, txtTelefono, txtPassword, txtConfirmaPassword;
    Button btnRegistrar;

    String tdoc[] ={"DNI", "Pasaporte", "Carnet Extranjeria", "Otros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final  RegistroActivity activity = this;
        service = new RegistroService(activity);

        setContentView(R.layout.activity_act_cuenta_nueva);

        asignarReferencia();
    }

    private void asignarReferencia() {
        spTipoDocumento = findViewById(R.id.spTipoDocumento);
        txtNumeroDocumento = findViewById(R.id.txtNumeroDocumento);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmaPassword = findViewById(R.id.txtConfirmaPassword);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tdoc);
        spTipoDocumento.setAdapter(adapter);

        //txtTelefono.setText(getPhoneNumber());
        capturaEventos();
    }

    private void capturaEventos() {

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msj = validaDatos();
                if (msj.equals(""))
                {
                    String tipoDoc, numeroDoc, nombres, apellidos, correo, telefono, password;

                    tipoDoc = spTipoDocumento.getSelectedItem().toString();
                    numeroDoc = txtNumeroDocumento.getText().toString();
                    nombres = txtNombres.getText().toString();
                    apellidos = txtApellidos.getText().toString();
                    correo = txtCorreo.getText().toString();
                    telefono = txtTelefono.getText().toString();
                    password = txtPassword.getText().toString();

                    service.registrar(tipoDoc, numeroDoc, nombres, apellidos, correo ,telefono, password);

                }else{
                    muestraMensaje("Validación", msj);
                }

            }
        });


    }

    private String validaDatos(){
        String msj = "";

        if (txtNumeroDocumento.getText().toString().equals("")){
            msj += "Ingrese el número de documento \n";
        }

        if (txtNombres.getText().toString().equals("")){
            msj += "Ingrese su nombre \n";
        }

        if (txtApellidos.getText().toString().equals("")){
            msj += "Ingrese sus apellidos \n";
        }

        if (txtCorreo.getText().toString().equals("")){
            msj += "Ingrese su correo \n";
        }else
        {
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            if(!pattern.matcher(txtCorreo.getText().toString()).matches()){
                msj += "El correo no es válido \n";
            }

        }

        if (txtTelefono.getText().toString().equals("")){
            msj += "Ingrese su teléfono \n";
        }else if (txtTelefono.getText().toString().length() != 9){
            msj += "Ingrese un número de celular válido ";
        }

        if (txtPassword.getText().toString().equals("")){
            msj += "Debe ingresar el Password";
        }else if(!txtPassword.getText().toString().equals(txtConfirmaPassword.getText().toString())){
            msj += "El Password no esta confirmado, ingrese el mismo password en ambas casillas";
        }

        return msj;
    }

    public void muestraMensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String getPhoneNumber(){
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getLine1Number();
    }



}