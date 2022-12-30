package com.rex.condominio.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rex.condominio.R;
import com.rex.condominio.fragments.AnunciosFragment;
import com.rex.condominio.fragments.DashboardFragment;
import com.rex.condominio.fragments.FamiliaFragment;
import com.rex.condominio.fragments.MiCuentaFragment;
import com.rex.condominio.fragments.ServiciosFragment;
import com.rex.condominio.fragments.TiendaFragment;
import com.rex.condominio.utils.SupportPreferences;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_bar = findViewById(R.id.bottom_bar);

        bottom_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_dashboard: {
                        SupportPreferences.loadFrament(new DashboardFragment(), getSupportFragmentManager().beginTransaction(), false);
                        return true;
                    }
                    case R.id.menu_anuncios: {
                        SupportPreferences.loadFrament(new AnunciosFragment(), getSupportFragmentManager().beginTransaction(), false);
                        return true;
                    }
                    case R.id.menu_servicios: {
                        SupportPreferences.loadFrament(new ServiciosFragment(), getSupportFragmentManager().beginTransaction(), false);
                        return true;
                    }
                    case R.id.menu_tienda: {
                        SupportPreferences.loadFrament(new TiendaFragment(), getSupportFragmentManager().beginTransaction(), false);
                        return true;
                    }
                    case R.id.menu_mi_cuenta: {
                        SupportPreferences.loadFrament(new MiCuentaFragment(), getSupportFragmentManager().beginTransaction(), false);
                        return true;
                    }
                    default:return false;
                }
            }
        });
        SupportPreferences.loadFrament(new DashboardFragment(), getSupportFragmentManager().beginTransaction(), false);
    }
}