package com.rex.condominio.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rex.condominio.R;
import com.rex.condominio.fragments.DashboardFragment;
import com.rex.condominio.fragments.admin.ComunidadesFragment;
import com.rex.condominio.fragments.admin.FamiliasFragment;
import com.rex.condominio.utils.SupportPreferences;

public class AdminActivity extends AppCompatActivity {

    private BottomNavigationView botton_nav_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        botton_nav_admin = findViewById(R.id.botton_nav_admin);
        botton_nav_admin.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.option_comunidades: {
                        SupportPreferences.loadFrament(new ComunidadesFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    case R.id.option_familias:{
                        SupportPreferences.loadFrament(new FamiliasFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
        SupportPreferences.loadFrament(new ComunidadesFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.fragment_container);
    }
}
