package com.dpm2020.appgas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dpm2020.appgas.data.Order;
import com.dpm2020.appgas.data.TuGasPreference;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    public TuGasPreference mTuGasPreference;
    private ProgressDialog progressDialog;
    private int start = 0;
    private int end = 0;
    public Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTuGasPreference = new TuGasPreference(getApplicationContext());
        this.updateCart();
    }

    public void updateCart() {
        String cartString = mTuGasPreference.getString("cart");
        if (cartString.equals("")) {
            this.order = new Order();
        } else {
            Gson gson = new Gson();
            this.order = gson.fromJson(cartString, Order.class);
        }
    }

    public void saveCart() {
        Gson gson = new Gson();
        String json = gson.toJson(this.order);
        mTuGasPreference.putString("cart", json);
    }

    /*
    public void addDetailOrder(String product_id, String name, int quantity, double price, double linetotal) {
        Order.Details details = new Order.Details(product_id, name, quantity, price, linetotal);
        order.AdicionaDet(details);
        this.saveCart();
    }
     */

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
