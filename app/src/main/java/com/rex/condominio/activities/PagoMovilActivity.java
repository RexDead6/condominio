package com.rex.condominio.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.adapters.PagoMovilAdapter;
import com.rex.condominio.dialogs.BancosDialog;
import com.rex.condominio.dialogs.CrearPagoMovilDialog;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoMovilActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recycler_pago_movil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_movil);

        recycler_pago_movil = findViewById(R.id.recycler_pago_movil);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getPagoMovil();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_pago_movil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_agregar_pago:{
                CrearPagoMovilDialog dialog = new CrearPagoMovilDialog(this, this);
                dialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }

    public void getPagoMovil(){
        Call<ResponseClient<ArrayList<PagoMovilResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getPagoMovil(
                SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new Callback<ResponseClient<ArrayList<PagoMovilResponse>>>() {
            @Override
            public void onResponse(Call<ResponseClient<ArrayList<PagoMovilResponse>>> call, Response<ResponseClient<ArrayList<PagoMovilResponse>>> response) {
                if (response.code() == 200){
                    recycler_pago_movil.setAdapter(new PagoMovilAdapter(response.body().getData(), new OnClickResponse<PagoMovilResponse>() {
                        @Override
                        public void onClick(PagoMovilResponse object) {
                            ProgressDialog progressDialog = new ProgressDialog(PagoMovilActivity.this);
                            progressDialog.show();

                            Call<ResponseClient<Object>> call1 = RetrofitClient.getInstance().getRequestInterface().updatePagomVenta(
                                    SupportPreferences.getInstance(PagoMovilActivity.this).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                    object.getIdPmv()+""
                            );
                            call1.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                                @Override
                                public Context returnContext() {
                                    return PagoMovilActivity.this;
                                }

                                @Override
                                public void onFinish() {
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void doCallBackResponse(ResponseClient<Object> response) {
                                    Toast.makeText(PagoMovilActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                    getPagoMovil();
                                }
                            });
                        }
                    }));
                    recycler_pago_movil.setLayoutManager(new LinearLayoutManager(PagoMovilActivity.this));
                }
            }

            @Override
            public void onFailure(Call<ResponseClient<ArrayList<PagoMovilResponse>>> call, Throwable t) {
                Log.e("PagoMovil", t.toString());
            }
        });
    }
}