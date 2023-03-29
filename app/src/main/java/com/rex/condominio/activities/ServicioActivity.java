package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.fragments.servicios.AdministrarServiciosFragment;
import com.rex.condominio.fragments.servicios.CrearServicioFragment;
import com.rex.condominio.utils.SupportPreferences;

public class ServicioActivity extends AppCompatActivity {

    private FloatingActionButton btn_open_servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        findViewById(android.R.id.content).setTransitionName("servicio");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        MaterialContainerTransform eet = new MaterialContainerTransform();
        eet.addTarget(android.R.id.content);
        eet.setDuration(300L);
        getWindow().setSharedElementEnterTransition(eet);

        MaterialContainerTransform ert = new MaterialContainerTransform();
        ert.addTarget(android.R.id.content);
        ert.setDuration(250L);
        getWindow().setSharedElementReturnTransition(ert);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);

        SupportPreferences.loadFrament(new AdministrarServiciosFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.container_servicios);

        btn_open_servicio = findViewById(R.id.btn_open_servicio);
        btn_open_servicio.setOnClickListener(v -> {
            SupportPreferences.loadFrament(new CrearServicioFragment(), getSupportFragmentManager().beginTransaction(), true, R.id.container_servicios);
            btn_open_servicio.animate()
                    .translationX(500)
                    .setDuration(650)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            btn_open_servicio.setVisibility(View.GONE);
                        }
                    });
        });
    }

    @Override
    public void onBackPressed() {
        btn_open_servicio.animate()
                .translationX(-5)
                .setDuration(650)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        btn_open_servicio.setVisibility(View.VISIBLE);
                    }
                });
        super.onBackPressed();
    }
}