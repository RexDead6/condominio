package com.rex.condominio.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ComunidadResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class DashboardFragment extends Fragment {

    private Spinner spin_comunidad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        spin_comunidad = v.findViewById(R.id.spin_comunidad);

        Call<ResponseClient<ArrayList<ComunidadResponse>>> callUrb = RetrofitClient.getInstance().getRequestInterface().getComunidadesUsuario(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        callUrb.enqueue(new ResponseCallback<ResponseClient<ArrayList<ComunidadResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ComunidadResponse>> response) {
                spin_comunidad.setAdapter(new ArrayAdapter<ComunidadResponse>(
                        getContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        response.getData()
                ));

                int idUrb = SupportPreferences.getInstance(getContext()).getPreferenceInt(
                        SupportPreferences.COMUNIDAD_ACTUAL_PREFERENCE
                );

                for (int i = 0; i<response.getData().size(); i++) {
                    if (response.getData().get(i).getIdUrb() == idUrb)
                        spin_comunidad.setSelection(i);
                }
            }
        });

        spin_comunidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ComunidadResponse comunidad = (ComunidadResponse)spin_comunidad.getSelectedItem();
                SupportPreferences.getInstance(getContext()).savePreferenceInt(
                        SupportPreferences.COMUNIDAD_ACTUAL_PREFERENCE,
                        comunidad.getIdUrb()
                );
                SupportPreferences.getInstance(getContext()).savePreference(
                        SupportPreferences.ADMIN_COMUNIDAD_PREFERENCE,
                        comunidad.isAdmin()+""
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }
}