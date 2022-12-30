package com.rex.condominio.activities.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.fragments.activarUser.ContainerFragment;

import at.markushi.ui.CircleButton;

public class ActivarUserActivity extends AppCompatActivity {

    private CircleButton btn_qr;

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
    }
}