package com.rex.condominio.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.FamiliaActivity;
import com.rex.condominio.activities.PagoMovilActivity;


public class MiCuentaFragment extends Fragment {

    private FloatingActionButton btn_pago_movil, btn_familia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_mi_cuenta, container, false);

        btn_pago_movil = v.findViewById(R.id.btn_pago_movil);
        btn_pago_movil.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), PagoMovilActivity.class);
            startActivity(intent);
        });

        btn_familia = v.findViewById(R.id.btn_familia);
        btn_familia.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), FamiliaActivity.class);
            startActivity(intent);
        });

        return v;
    }
}