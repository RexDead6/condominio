package com.rex.condominio.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.UsuarioRequest;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ImageView btn_back;
    private TextInputEditText et_cedula, et_nombre, et_apellido, et_telefono, et_clave, et_clave1;
    private RadioGroup radio_group;
    private RadioButton radio_m, radio_f;
    private MaterialButton btn_registrar;
    private String currentGenero = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_back = findViewById(R.id.btn_back);
        et_cedula = findViewById(R.id.et_cedula);
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_telefono = findViewById(R.id.et_telefono);
        radio_group = findViewById(R.id.radio_group);
        radio_m = findViewById(R.id.radio_m);
        radio_f = findViewById(R.id.radio_f);
        et_clave = findViewById(R.id.et_clave);
        et_clave1 = findViewById(R.id.et_clave1);
        btn_registrar = findViewById(R.id.btn_registrar);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                currentGenero = ((RadioButton) findViewById(i)).getText().toString();
            }
        });

        btn_back.setOnClickListener(V -> finish());
        btn_registrar.setOnClickListener(this::registrarseButtom);
    }

    public void registrarseButtom(View view){
        if (!validateInputs()) return;

        UsuarioRequest usuarioRequest = new UsuarioRequest(
                et_cedula.getText().toString(),
                et_nombre.getText().toString(),
                et_apellido.getText().toString(),
                currentGenero,
                et_telefono.getText().toString(),
                et_clave.getText().toString()
        );

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        Call<ResponseClient<TokenResponse>> call = RetrofitClient.getInstance().getRequestInterface().insertUsuario(usuarioRequest);
        call.enqueue(new Callback<ResponseClient<TokenResponse>>() {
            @Override
            public void onResponse(Call<ResponseClient<TokenResponse>> call, Response<ResponseClient<TokenResponse>> response) {
                progressDialog.dismiss();
                if (response.code() == 201){
                    Intent intent = new Intent(RegisterActivity.this, ActivarUserActivity.class);
                    SupportPreferences.getInstance(RegisterActivity.this).savePreference(SupportPreferences.TOKEN_PREFERENCE, response.body().getData().getToken());
                    startActivity(intent);
                    finish();
                } else {
                    new MaterialAlertDialogBuilder(RegisterActivity.this)
                            .setMessage(response.body().getMessage())
                            .setPositiveButton("Aceptar", (d, w) -> d.dismiss())
                            .create().show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClient<TokenResponse>> call, Throwable t) {
                Log.e("UserInsert", t.toString());
                progressDialog.dismiss();
            }
        });
    }

    public boolean validateInputs(){
        boolean success = true;

        if (et_cedula.getText().toString().isEmpty()){
            success = false;
            et_cedula.setError("Cedula no puede estar vacio");
        }

        if (et_nombre.getText().toString().isEmpty()){
            success = false;
            et_nombre.setError("Nombre no puede estar vacio");
        }

        if (et_apellido.getText().toString().isEmpty()){
            success = false;
            et_apellido.setError("Apellido no puede estar vacio");
        }

        if (et_telefono.getText().toString().isEmpty()){
            success = false;
            et_telefono.setError("Telefono no puede estar vacio");
        }

        if (!radio_m.isChecked() && !radio_f.isChecked()){
            radio_m.setError("Seleccione un genero");
            success = false;
        }

        if (et_clave.getText().toString().isEmpty()){
            success = false;
            et_clave.setError("Clave no puede estar vacio");
        }

        if (et_clave1.getText().toString().isEmpty()){
            success = false;
            et_clave1.setError("Confirmación de clave no puede estar vacio");
        }

        if (!et_clave.getText().toString().equals(et_clave1.getText().toString())){
            success = false;
            et_clave1.setError("Confirmacion de clave debe ser igual a clave");
        }

        return success;
    }
}