package com.rex.condominio.fragments.activarUser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.transition.MaterialElevationScale;
import com.rex.condominio.R;

import at.markushi.ui.CircleButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContainerFragment extends Fragment {

    private CircleButton btn_qr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);
        setExitTransition(new MaterialElevationScale(true));
        setReenterTransition(new MaterialElevationScale(true));

        btn_qr = v.findViewById(R.id.btn_qr);
        btn_qr.setOnClickListener(V -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    //.addSharedElement(btn_qr, "qr_transition")
                    .replace(R.id.fragment_container_activar_user, new QrScannerFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return v;
    }
}