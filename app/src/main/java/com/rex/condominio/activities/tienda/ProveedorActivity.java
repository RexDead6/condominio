package com.rex.condominio.activities.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ProveedorAdapter;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.ProveedorRequest;
import com.rex.condominio.retrofit.response.ProveedorResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class ProveedorActivity extends AppCompatActivity {

    private View view_nof_found;
    private RecyclerView recycler_servicios;
    private LottieAnimationView animationView;
    private FloatingActionButton btn_agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);

        view_nof_found = findViewById(R.id.view_nof_found);
        recycler_servicios = findViewById(R.id.recycler_servicios);
        animationView = findViewById(R.id.animationView);
        btn_agregar = findViewById(R.id.btn_agregar);

        btn_agregar.setOnClickListener(V -> {
            Dialog dialog = new Dialog(ProveedorActivity.this);
            dialog.setContentView(R.layout.modal_agregar_proveedor);

            dialog.findViewById(R.id.btn_guardar).setOnClickListener(V1 -> {
                TextInputEditText et_rif = dialog.findViewById(R.id.et_rif);
                TextInputEditText et_nombre = dialog.findViewById(R.id.et_nombre);

                boolean success = true;

                if (et_rif.getText().toString().isEmpty()){
                    et_rif.setError("Debe ingresar el numero de documento");
                    success = false;
                }

                if (et_nombre.getText().toString().isEmpty()){
                    et_nombre.setError("Debe ingresar el nombre del proveedor");
                    success = false;
                }

                if (!success) return;

                ProveedorRequest proveedorRequest = new ProveedorRequest(
                        et_rif.getText().toString(),
                        et_nombre.getText().toString()
                );

                ProgressDialog progressDialog = new ProgressDialog(ProveedorActivity.this);
                progressDialog.show();

                Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertProveedor(
                        SupportPreferences.getInstance(ProveedorActivity.this).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                        proveedorRequest
                );
                call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                    @Override
                    public Context returnContext() {
                        return ProveedorActivity.this;
                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void doCallBackResponse(ResponseClient<Object> response) {
                        animationView.setVisibility(View.VISIBLE);
                        recycler_servicios.setVisibility(View.GONE);
                        view_nof_found.setVisibility(View.GONE);
                        getProveedores();
                        dialog.dismiss();
                    }
                });
            });
            dialog.show();
        });

        getProveedores();
    }

    private void getProveedores(){
        Call<ResponseClient<ArrayList<ProveedorResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getProveedor(
                SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ProveedorResponse>>>() {
            @Override
            public Context returnContext() {
                return ProveedorActivity.this;
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ProveedorResponse>> response) {
                recycler_servicios.setAdapter(new ProveedorAdapter(response.getData(), new OnClickResponse<ProveedorResponse>() {
                    @Override
                    public void onClick(ProveedorResponse object) {
                        Intent intent = new Intent();
                        intent.putExtra("proveedor", object);
                        setResult(3, intent);
                        finish();
                    }
                }));
                recycler_servicios.setLayoutManager(new LinearLayoutManager(ProveedorActivity.this));
                recycler_servicios.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_nof_found.setVisibility(View.VISIBLE);
            }
        });
    }
}