package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rex.condominio.R;
import com.rex.condominio.fragments.servicios.AdministrarServiciosFragment;
import com.rex.condominio.utils.SupportPreferences;

public class ServicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);

        SupportPreferences.loadFrament(new AdministrarServiciosFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.container_servicios);
    }
}