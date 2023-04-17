package com.rex.condominio.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.tienda.ProductosActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TiendaFragment extends Fragment {

    private FloatingActionButton btn_productos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tienda, container, false);

        btn_productos = v.findViewById(R.id.btn_productos);
        btn_productos.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductosActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    btn_productos,
                    "producto"
            );
            startActivity(intent, options.toBundle());
        });

        return v;
    }
}