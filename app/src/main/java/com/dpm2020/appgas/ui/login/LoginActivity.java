package com.dpm2020.appgas.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dpm2020.appgas.BaseActivity;
import com.dpm2020.appgas.R;
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
        Log.i("TUGAS", noShowOnboarding ? "NO SHOW" : "SHOW");
        mTuGasPreference.setOnBoarding(true);
        noShowOnboarding = mTuGasPreference.getOnBoarding();
        Log.i("TUGAS", noShowOnboarding ? "NO SHOW" : "SHOW");

        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.txtUsername);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btnLogin);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        final TextView btn_register = findViewById(R.id.textView13);
        final TextView btn_register2 = findViewById(R.id.textView14);

        usernameEditText.setText("manueltemple@gmail.com");
        passwordEditText.setText("123123");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: redirect
                //startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });

        btn_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: redirect
                //startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
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
    }
}