package com.rex.condominio.fragments.servicios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rex.condominio.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CrearServicioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_servicio, container, false);



        return v;
    }
}