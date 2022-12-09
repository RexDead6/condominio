package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.rex.condominio.R;
import com.rex.condominio.dialogs.DialogProgress;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.response.LoginResponse;
import com.rex.condominio.retrofit.response.ResponseClient;

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

            DialogProgress dialogProgress = new DialogProgress(this);
            dialogProgress.show();

            LoginRequest loginRequest = new LoginRequest(
                    et_cedula.getText().toString(),
                    et_clave.getText().toString()
            );

            Call<ResponseClient<LoginResponse>> clientCall = RetrofitClient.getInstance().getRequestInterface().login(loginRequest);
            clientCall.enqueue(new Callback<ResponseClient<LoginResponse>>() {
                @Override
                public void onResponse(Call<ResponseClient<LoginResponse>> call, Response<ResponseClient<LoginResponse>> response) {
                    dialogProgress.dismiss();
                    if (response.body().isStatus()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }


                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(response.body().getMessage())
                            .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                            .create().show();
                }

                @Override
                public void onFailure(Call<ResponseClient<LoginResponse>> call, Throwable t) {
                    Log.e("login", t.toString());
                    dialogProgress.dismiss();
                }
            });
        });
    }

    private boolean validate_inputs(){
        boolean success = true;
        if (et_cedula.getText().toString().isEmpty()){
            et_cedula.setError("Ingrese su cedula");
            success = false;
        }

        if (et_clave.getText().toString().isEmpty()){
            et_clave.setError("Ingrese su clave");
            success = false;
        }
        return success;
    }
}