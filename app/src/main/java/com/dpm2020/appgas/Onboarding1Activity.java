package com.dpm2020.appgas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dpm2020.appgas.network.service.LoginService;
import com.dpm2020.appgas.ui.login.LoginActivity;

public class Onboarding1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean noShowOnboarding = mTuGasPreference.getOnBoarding();

        if (noShowOnboarding) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_onboarding1);

        final TextView nextButton = findViewById(R.id.btn_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Onboarding2Activity.class));
            }
        });
    }
}