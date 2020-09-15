package com.dpm2020.appgas;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Localizacion implements LocationListener {

    DireccionActivity actdireccion;
    TextView txtMensaje;

    //SearchView sv_direccion;

    public DireccionActivity getMainActivity() {
        return actdireccion;
    }

    //, SearchView sv_direccion
    public void setMainActivity(DireccionActivity actdireccion, TextView txtDireccion) {
        this.actdireccion = actdireccion;
        this.txtMensaje = txtDireccion;
       // this.sv_direccion = sv_direccion;
    }

    @Override
    public void onLocationChanged(Location location) {
        //Este método se ejecuta cuando el GPS recibe nuevas coordenadas
       /* String texto = "Mi ubicación es: "+
                        "\nLatitud = "+location.getLatitude()+
                        "\nLongitud = "+location.getLongitude();
        txtMensaje.setText(texto);
        mapa(location.getLatitude(), location.getLongitude());

        */
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status){
                case LocationProvider.AVAILABLE:
                    Log.d("=>","Disponible");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("=>","Fuera de servicio");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("=>","Unavailable");
                    break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        txtMensaje.setText("GPS Activado");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        txtMensaje.setText("GPS Desactivado");
    }

    public void mapa(double lat, double lon){
        FragmentMaps fragmento = new FragmentMaps();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", new Double(lat));
        bundle.putDouble("lon", new Double(lon));
        fragmento.setArguments(bundle);

        FragmentManager manager = getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, fragmento,null);
        transaction.commit();
    }
}
