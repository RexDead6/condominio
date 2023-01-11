package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.register.RegisterActivity;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import at.markushi.ui.CircleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_cedula, et_clave;
    private FrameLayout btn_registrate;
    private CircleButton btn_entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_cedula = findViewById(R.id.et_cedula);
        et_clave = findViewById(R.id.et_clave);
        btn_registrate = findViewById(R.id.btn_registrate);
        btn_entrar = findViewById(R.id.btn_entrar);

        btn_registrate.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btn_entrar.setOnClickListener(v -> {
            if (!validate_inputs()) return;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            LoginRequest loginRequest = new LoginRequest(
                    et_cedula.getText().toString(),
                    et_clave.getText().toString()
            );

            Call<ResponseClient<TokenResponse>> clientCall = RetrofitClient.getInstance().getRequestInterface().login(loginRequest);
            clientCall.enqueue(new Callback<ResponseClient<TokenResponse>>() {
                @Override
                public void onResponse(Call<ResponseClient<TokenResponse>> call, Response<ResponseClient<TokenResponse>> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        SupportPreferences.getInstance(LoginActivity.this).savePreference(SupportPreferences.TOKEN_PREFERENCE, response.body().getData().getToken());
                        if (!new TokenSupport(LoginActivity.this).getIdFam().equals("00")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return;
                    }

                    ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(errorResponse.getMessage())
                            .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                            .create().show();
                }

                @Override
                public void onFailure(Call<ResponseClient<TokenResponse>> call, Throwable t) {
                    Log.e("login", t.toString());
                    progressDialog.dismiss();
                }
            });
        });
    }

    private boolean validate_inputs() {
        boolean success = true;
        if (et_cedula.getText().toString().isEmpty()) {
            et_cedula.setError("Ingrese su cedula");
            success = false;
        }

        if (et_clave.getText().toString().isEmpty()) {
            et_clave.setError("Ingrese su clave");
            success = false;
        }
        return success;
    }
}