package com.dpm2020.appgas.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dpm2020.appgas.R;
import com.dpm2020.appgas.TiendaActivity;
import com.dpm2020.appgas.data.TuGasPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private final String URL_LOGIN = "https://apigas.komanda.pe/auth/token/auth";
    private TuGasPreference mTuGasPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.mTuGasPreference = new TuGasPreference(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.txtUsername);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btnLogin);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        final TextView btn_register = findViewById(R.id.textView13);
        final TextView btn_register2 = findViewById(R.id.textView14);

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

        /*
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                //loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });
        */

        /*
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
        */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "clic", Toast.LENGTH_SHORT).show();
                /*
                startActivity(new Intent(getApplicationContext(), TiendaActivity.class));
                */

                loadingProgressBar.setVisibility(View.VISIBLE);

                wsLogin();
                /*
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                 */
            }
        });
    }
    /*
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    */

    private void loadingHide() {
        ProgressBar loadingProgressBar = findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void wsLogin() {
        JSONObject jsonBody = new JSONObject();
        EditText usernameEditText = findViewById(R.id.txtUsername);
        EditText passwordEditText = findViewById(R.id.password);

        try {
            jsonBody.put("username", usernameEditText.getText());
            jsonBody.put("password", passwordEditText.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingHide();
                // JSONObject jsonObject = JSONObject(response);
                Log.i("TUGAS", response.toString());
                try {
                    mTuGasPreference.setToken(response.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(), TiendaActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingHide();
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}