package com.rex.condominio.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.fragments.familia.ListaFamiliaFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.FamiliaRequest;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;

public class CrearFamiliaDialog extends Dialog {

    private TextInputEditText et_descripcion, et_direccion, et_usuario;
    private MaterialButton btn_guardar;
    private int idJefeUsu;
    private ListaFamiliaFragment fragment;

    public CrearFamiliaDialog(@NonNull Context context, ListaFamiliaFragment fragment) {
        super(context);
        this.fragment = fragment;
        onCreate();
    }

    private void onCreate(){
        setContentView(R.layout.modal_crear_familia);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        et_descripcion = findViewById(R.id.et_descripcion);
        et_direccion = findViewById(R.id.et_direccion);
        et_usuario = findViewById(R.id.et_usuario);
        btn_guardar = findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(this::btnEnviar);

        et_usuario.setOnClickListener(V -> {
            UsuariosInactivosDialog dialog = new UsuariosInactivosDialog(getContext());
            dialog.onCreate(new OnClickResponse<UsuarioResponse>() {
                @Override
                public void onClick(UsuarioResponse usuarioResponse) {
                    et_usuario.setText(usuarioResponse.getCedUsu());
                    idJefeUsu = usuarioResponse.getIdUsu();
                }
            });
            dialog.show();
        });
    }

    public void btnEnviar(View view){
        if (!validateInputs()) return;

        FamiliaRequest familiaRequest = new FamiliaRequest(
                idJefeUsu,
                et_descripcion.getText().toString().trim(),
                et_direccion.getText().toString().trim()
        );

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertFamilia(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                familiaRequest
        );
        call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<Object> response) {
                new AlertDialog.Builder(returnContext())
                        .setMessage("Familia registrada con exito")
                        .setPositiveButton("Aceptar", (d, v) -> {
                            d.dismiss();
                            fragment.reloadView();
                        })
                        .create().show();
                dismiss();
            }
        });
    }

    private boolean validateInputs(){
        boolean success = true;

        if (et_descripcion.getText().toString().trim().isEmpty()){
            et_descripcion.setError("Ingrese el apellido de la familia");
            success = false;
        }

        if (et_direccion.getText().toString().trim().isEmpty()){
            et_direccion.setError("Ingrese la direccion de la familia");
            success = false;
        }

        if (et_usuario.getText().toString().trim().isEmpty()){
            et_usuario.setError("Ingrese la direccion de la familia");
            success = false;
        }

        return success;
    }
}
