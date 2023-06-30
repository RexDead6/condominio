package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.register.ActivarUserActivity;
import com.rex.condominio.activities.register.RegisterActivity;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
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
            clientCall.enqueue(new ResponseCallback<ResponseClient<TokenResponse>>() {
                @Override
                public Context returnContext() {
                    return LoginActivity.this;
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                }

                @Override
                public void doCallBackResponse(ResponseClient<TokenResponse> response) {
                    SupportPreferences.getInstance(LoginActivity.this).savePreference(SupportPreferences.TOKEN_PREFERENCE, response.getData().getToken());
                    SupportPreferences.getInstance(LoginActivity.this).savePreference(SupportPreferences.JEFE_FAMILIA_PREFERENCE, String.valueOf(response.getData().isJefe()));
                    TokenSupport token = new TokenSupport(LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, ActivarUserActivity.class);
                    if (!token.getIdFam().equals("00")) {
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
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

    @Override
    public void onBackPressed() {
        finish();
    }
}