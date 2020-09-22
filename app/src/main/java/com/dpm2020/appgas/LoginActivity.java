package com.dpm2020.appgas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dpm2020.appgas.network.service.LoginService;

public class LoginActivity extends BaseActivity {
    LoginActivity activity;
    LoginService services;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LoginActivity activity = this;
        services = new LoginService(activity);

        Boolean noShowOnboarding = mTuGasPreference.getOnBoarding();
        mTuGasPreference.setOnBoarding(true);

        String token = mTuGasPreference.getToken();
        if (!token.equals("")) {
            LoginService loginServices = new LoginService(this);
            loginServices.verify();
        }

        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.txtUsername);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btnLogin);

        final TextView btn_register = findViewById(R.id.textView13);
        final TextView btn_register2 = findViewById(R.id.textView14);

        final TextView btnCentralTelefonica = findViewById(R.id.textViewCentral);

        usernameEditText.setText("manueltemple@gmail.com");
        passwordEditText.setText("123123");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });

        btn_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(usernameEditText.getText());
                String password = String.valueOf(passwordEditText.getText());

                services.login(username, password);
            }
        });

        btnCentralTelefonica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ("tel:956761550");
                Intent mIntent = new Intent(Intent.ACTION_CALL);
                mIntent.setData(Uri.parse(number));
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE}, 0);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(mIntent);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}