package com.rex.condominio.activities.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.rex.condominio.R;
import com.rex.condominio.fragments.tienda.CompraFragment;
import com.rex.condominio.fragments.tienda.CrearCompraFragment;
import com.rex.condominio.fragments.tienda.CrearProductoFragment;
import com.rex.condominio.fragments.tienda.ProductosAdminFragment;
import com.rex.condominio.utils.SupportPreferences;

public class ProductosActivity extends AppCompatActivity {

    private FloatingActionButton btn_agregar, btn_comprar;
    private LinearLayout container_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        findViewById(android.R.id.content).setTransitionName("producto");
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
        setContentView(R.layout.activity_productos);

        btn_agregar = findViewById(R.id.btn_agregar);
        btn_comprar = findViewById(R.id.btn_comprar);
        container_btn = findViewById(R.id.container_btn);

        btn_agregar.setOnClickListener(V -> {
            SupportPreferences.loadFrament(new CrearProductoFragment(), getSupportFragmentManager().beginTransaction(), true, R.id.container_productos);
            translateButtons();
        });

        btn_comprar.setOnClickListener(V -> {
            SupportPreferences.loadFrament(new CompraFragment(), getSupportFragmentManager().beginTransaction(), true, R.id.container_productos);
            translateButtons();
        });

        SupportPreferences.loadFrament(new ProductosAdminFragment(), getSupportFragmentManager().beginTransaction(), false, R.id.container_productos);
    }

    private void translateButtons(){
        container_btn.animate()
                .translationX(500)
                .setDuration(650)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        btn_agregar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        container_btn.animate()
                .translationX(-5)
                .setDuration(650)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        btn_agregar.setVisibility(View.VISIBLE);
                    }
                });
        super.onBackPressed();
    }
}