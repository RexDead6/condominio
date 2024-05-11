package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.adapters.FamiliaAdapter;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.fragments.familia.FamiliaFragment;
import com.rex.condominio.fragments.familia.ListaFamiliaFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamiliaActivity extends AppCompatActivity {

    private FloatingActionButton btn_familias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familt);

        btn_familias = findViewById(R.id.btn_familias);

        SupportPreferences.loadFrament(new FamiliaFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.container_familia);

        String isAdmin = SupportPreferences.getInstance(this).getPreference(SupportPreferences.ADMIN_COMUNIDAD_PREFERENCE);
        if (isAdmin.equals("false")){
            btn_familias.setVisibility(View.GONE);
        }
    }

    public void openFamilias(View view){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        /*
        Call<ResponseClient<ArrayList<FamiliaResponse>>> getAll = RetrofitClient.getInstance().getRequestInterface().getAllFamilia(
                SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        */
        Call<ResponseClient<ArrayList<FamiliaResponse>>> getAll = RetrofitClient.getInstance().getRequestInterface().getFamByComunidad(
                ""+SupportPreferences.getInstance(this).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE)
        );
        getAll.enqueue(new ResponseCallback<ResponseClient<ArrayList<FamiliaResponse>>>() {
            @Override
            public Context returnContext() {
                return FamiliaActivity.this;
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<FamiliaResponse>> response) {
                new SupportPreferences.ObjectPreference<FamiliaResponse>().saveObject(
                        FamiliaActivity.this,
                        response.getData(),
                        SupportPreferences.FAMILIA_LIST_OBJECT
                );
                SupportPreferences.loadFrament(new ListaFamiliaFragment(), getSupportFragmentManager().beginTransaction(), true, R.id.container_familia);
                btn_familias.animate()
                        .translationX(500)
                        .setDuration(650)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                btn_familias.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        btn_familias.animate()
                .translationX(-5)
                .setDuration(650)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        btn_familias.setVisibility(View.VISIBLE);
                    }
                });
        super.onBackPressed();
    }
}