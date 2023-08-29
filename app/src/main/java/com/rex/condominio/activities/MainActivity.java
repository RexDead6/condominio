package com.rex.condominio.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.fragments.AnunciosFragment;
import com.rex.condominio.fragments.DashboardFragment;
import com.rex.condominio.fragments.MiCuentaFragment;
import com.rex.condominio.fragments.ServiciosFragment;
import com.rex.condominio.fragments.TiendaFragment;
import com.rex.condominio.services.NotificacionesService;
import com.rex.condominio.utils.SupportPreferences;

public class MainActivity extends AppCompatActivity {

    public MainActivity(){
        Log.e("class", "init");
    }

    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_bar = findViewById(R.id.bottom_bar);

        Intent intentService = new Intent(this, NotificacionesService.class);
        startService(intentService);

        bottom_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_dashboard: {
                        SupportPreferences.loadFrament(new DashboardFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    case R.id.menu_anuncios: {
                        SupportPreferences.loadFrament(new AnunciosFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    case R.id.menu_servicios: {
                        SupportPreferences.loadFrament(new ServiciosFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    case R.id.menu_tienda: {
                        SupportPreferences.loadFrament(new TiendaFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    case R.id.menu_mi_cuenta: {
                        SupportPreferences.loadFrament(new MiCuentaFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    default:return false;
                }
            }
        });
        SupportPreferences.loadFrament(new DashboardFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
    }

    private void cargarVariables() {

    }
}