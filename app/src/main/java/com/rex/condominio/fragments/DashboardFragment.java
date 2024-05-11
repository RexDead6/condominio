package com.rex.condominio.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rex.condominio.R;
import com.rex.condominio.adapters.AnunciosAdapter;
import com.rex.condominio.adapters.ServiciosPorPagarAdapter;
import com.rex.condominio.fragments.servicios.PagarServicioFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.AjusteResponse;
import com.rex.condominio.retrofit.response.ComunidadResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.retrofit.response.StatiticResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class DashboardFragment extends Fragment {

    private Spinner spin_comunidad;
    private TextView label_monto, label_total;
    private RecyclerView recycler_anuncios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        spin_comunidad = v.findViewById(R.id.spin_comunidad);
        label_monto = v.findViewById(R.id.label_monto);
        label_total = v.findViewById(R.id.label_total);
        recycler_anuncios = v.findViewById(R.id.recycler_anuncios);

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

                call_statics();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        call_statics();
        return v;
    }

    private void call_statics(){
        Call<ResponseClient<StatiticResponse>> call = RetrofitClient.getInstance().getRequestInterface().getStatitic(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_PREFERENCE) + ""
        );
        call.enqueue(new ResponseCallback<ResponseClient<StatiticResponse>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<StatiticResponse> response) {
                recycler_anuncios.setAdapter(new AnunciosAdapter(response.getData().getUltimosAnuncios(), getContext()));
                recycler_anuncios.setLayoutManager(new LinearLayoutManager(getContext()));
                callTasa(response.getData().getServiciosPorPagar());
            }
        });
    }

    private void callTasa(ArrayList<ServicioResponse> data){
        Call<ResponseClient<AjusteResponse>> callDivisa = RetrofitClient.getInstance().getRequestInterface().getAjuste(
                "DIVISA"
        );
        callDivisa.enqueue(new ResponseCallback<ResponseClient<AjusteResponse>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<AjusteResponse> response) {
                label_total.setText(data.size()+"");
                float total = 0;

                for (ServicioResponse servicio: data){
                    float monto = 0;
                    if (servicio.getDivisa() == 0) {
                        monto = servicio.getMontoSer();
                    } else {
                        monto = servicio.getMontoSer() * Float.parseFloat(response.getData().getValue());
                    }
                    total = total + (monto * servicio.getMesesPorPagar());
                }

                label_monto.setText(SupportPreferences.formatCurrency(total) + " Bs");
            }
        });
    }
}