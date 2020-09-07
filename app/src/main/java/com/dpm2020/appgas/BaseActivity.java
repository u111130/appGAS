package com.dpm2020.appgas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dpm2020.appgas.data.TuGasPreference;

public class BaseActivity extends AppCompatActivity {
    public TuGasPreference mTuGasPreference;
    private ProgressDialog progressDialog;
    private int start = 0;
    private int end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTuGasPreference = new TuGasPreference(getApplicationContext());
    }

    public String getSaludo() {
        return "Hola "+ mTuGasPreference.getUserName();
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this, R.style.AppTheme_ProgressBar) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.layout_progress);
                    getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                }
            };
            progressDialog.setCancelable(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
