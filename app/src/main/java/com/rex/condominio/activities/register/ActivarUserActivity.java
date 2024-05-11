package com.rex.condominio.activities.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.fragments.activarUser.ContainerFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import at.markushi.ui.CircleButton;
import retrofit2.Call;

public class ActivarUserActivity extends AppCompatActivity {

    private FloatingActionButton btn_logout;
    private Thread receibeThread;
    private boolean needRunning = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_user);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_activar_user, new ContainerFragment())
                .commit();

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(ActivarUserActivity.this);
                dialog.show();
                Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().logout(
                        SupportPreferences.getInstance(ActivarUserActivity.this).getPreference(SupportPreferences.TOKEN_PREFERENCE)
                );
                call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                    @Override
                    public Context returnContext() {
                        return ActivarUserActivity.this;
                    }

                    @Override
                    public void onFinish() {
                        dialog.dismiss();
                    }

                    @Override
                    public void doCallBackResponse(ResponseClient<Object> response) {
                        SupportPreferences.getInstance(ActivarUserActivity.this).savePreference(SupportPreferences.TOKEN_PREFERENCE, "");
                        Intent intent = new Intent(ActivarUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        receibeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (needRunning){
                    Call<ResponseClient<UsuarioResponse>> call = RetrofitClient.getInstance().getRequestInterface().getUsuario(
                            new TokenSupport(ActivarUserActivity.this).getIdUsu()+""
                    );
                    call.enqueue(new ResponseCallback<ResponseClient<UsuarioResponse>>() {
                        @Override
                        public Context returnContext() {
                            return ActivarUserActivity.this;
                        }

                        @Override
                        public void onFinish() {}

                        @Override
                        public void doCallBackResponse(ResponseClient<UsuarioResponse> response) {
                            try {
                                if (response.getData().getStatusUsu() == 1) {
                                    new AlertDialog.Builder(ActivarUserActivity.this)
                                        .setMessage("Su usuario ha sido activado, inicie sesion para continuar")
                                        .setPositiveButton("Aceptar", (d, w) -> {

                                                SupportPreferences.getInstance(ActivarUserActivity.this).savePreference(SupportPreferences.TOKEN_PREFERENCE, "");
                                                Intent intent = new Intent(ActivarUserActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                        }).create().show();
                                needRunning = false;
                            }
                            }catch(Exception e) {}
                        }
                    });

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        receibeThread.start();
    }

    @Override
    protected void onDestroy() {
        needRunning = false;
        super.onDestroy();
    }
}