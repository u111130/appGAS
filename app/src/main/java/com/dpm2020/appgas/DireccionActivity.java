package com.dpm2020.appgas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.dpm2020.appgas.network.service.DireccionService;
import com.dpm2020.appgas.network.service.RegistroService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


public class DireccionActivity extends BaseActivity {

    TextView txtMensaje, txtInterior;
    SearchView sv_direccion;
    Button btnRegistraDir;
    Button btnCancelar;

    LatLng latLng;
    DireccionService service;
    DireccionActivity activity;

    private static final long MIN_TIME = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final  DireccionActivity activity = this;
        service = new DireccionService(activity);

        setContentView(R.layout.activity_act_direccion);
        asignarReferencias();

    }

    private void asignarReferencias() {
        txtMensaje = findViewById(R.id.txtMensaje);
        txtInterior = findViewById(R.id.txtInterior);
        sv_direccion = findViewById(R.id.sv_direccion);
        btnRegistraDir = findViewById(R.id.btnRegistraDir);
        btnCancelar = findViewById(R.id.btnCancelar);

        sv_direccion.onActionViewExpanded();


        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }else{
            iniciarLocalizacion();
        }


    }

    private void iniciarLocalizacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Localizacion localizacion = new Localizacion();

        localizacion.setMainActivity(this,txtMensaje);

        final boolean gpsHabilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsHabilitado){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,localizacion);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,localizacion);

        txtMensaje.setText("Localización agregada");


        sv_direccion.setOnQueryTextListener( new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = sv_direccion.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(DireccionActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    latLng =  new LatLng(address.getLatitude(),address.getLongitude());
                    localizacion.mapa(latLng.latitude, latLng.longitude);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        btnRegistraDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msj = validaDatos();
                if (msj.equals(""))
                {
                    String nombre = sv_direccion.getQuery().toString();
                    String interior = txtInterior.getText().toString();
                    double lat = latLng.latitude;
                    double lon = latLng.longitude;

                    service.registrar(nombre, interior, lat, lon);

                    Intent intent = getIntent();
                    if (intent != null) {
                        setResult(1, intent);
                    }
                    finish();

                }else{
                    muestraMensaje("Validación", msj);
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent != null) {
                    setResult(0, intent);
                }
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                iniciarLocalizacion();

                return;
            }
        }
    }


    private String validaDatos(){
        String msj = "";

        if (sv_direccion.getQuery().toString().equals("")){
            msj += "Ingrese la dirección \n";
        }

        if (latLng.longitude == 0 || latLng.latitude == 0){
            msj += "Ubique una posición en el mapa. \n";
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


}