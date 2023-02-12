package com.rex.condominio.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.activities.PagoMovilActivity;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.PagoMovilRequest;
import com.rex.condominio.retrofit.response.BancosResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearPagoMovilDialog extends Dialog {

    private TextInputEditText et_cedula;
    private TextInputEditText et_telefono;
    private TextInputEditText et_banco;
    private BancosResponse bancoActual;
    private PagoMovilActivity parent;

    public CrearPagoMovilDialog(@NonNull Context context, PagoMovilActivity parent) {
        super(context);
        this.parent = parent;
        createDialog();
    }

    private void createDialog(){
        setContentView(R.layout.modal_registrar_pago_movil);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        et_cedula = findViewById(R.id.et_cedula);
        et_telefono = findViewById(R.id.et_telefono);
        et_banco = findViewById(R.id.et_banco);

        et_banco.setOnClickListener(V -> {
            BancosDialog dialog1 = new BancosDialog(getContext(), this);
            dialog1.show();
        });

        findViewById(R.id.btn_guardar).setOnClickListener(V -> {
            if (!validateForms()) return;

            PagoMovilRequest pagoMovilRequest = new PagoMovilRequest(
                    bancoActual.getIdBan(),
                    et_telefono.getText().toString(),
                    et_cedula.getText().toString()
            );

            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insetPagoMovil(
                    SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                    pagoMovilRequest
            );

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();

            call.enqueue(new Callback<ResponseClient<Object>>() {
                @Override
                public void onResponse(Call<ResponseClient<Object>> call, Response<ResponseClient<Object>> response) {
                    progressDialog.dismiss();
                    if (response.code() == 201){
                        parent.getPagoMovil();
                        dismiss();
                        return;
                    }

                    ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

                    new AlertDialog.Builder(getContext())
                            .setMessage(errorResponse.getMessage())
                            .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                            .create().show();
                }

                @Override
                public void onFailure(Call<ResponseClient<Object>> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("insertPagoMovil", t.toString());
                }
            });
        });
    }

    private boolean validateForms(){
        boolean SUCCESS = true;

        if (et_banco.getText().toString().trim().isEmpty()){
            et_banco.setError("Ingrese el Banco");
            SUCCESS = false;
        }

        if (et_cedula.getText().toString().trim().length() < 6){
            et_cedula.setError("Cedula debe incluir mas de 6 numeros o mas");
            SUCCESS = false;
        }

        if (et_telefono.getText().toString().trim().length() != 11){
            et_telefono.setError("Numero telefonico debe contener 11 numeros");
            SUCCESS = false;
        }

        return SUCCESS;
    }

    public void obtenerBanco(BancosResponse banco){
        et_banco.setText(banco.getNomBan());
        bancoActual = banco;
    }
}