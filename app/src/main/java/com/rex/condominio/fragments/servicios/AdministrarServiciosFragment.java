package com.rex.condominio.fragments.servicios;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ServiciosAdminAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.AjusteResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AdministrarServiciosFragment extends Fragment {

    private RecyclerView recycler_servicio;
    private LottieAnimationView animationView;
    private View view_not_found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_administrar_servicios, container, false);

        recycler_servicio = v.findViewById(R.id.recycler_servicio);
        animationView = v.findViewById(R.id.animationView);
        view_not_found = v.findViewById(R.id.view_not_found);

        onCallData();

        return v;
    }

    private void onCallData(){
        Call<ResponseClient<ArrayList<ServicioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getAdminServicio(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ServicioResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ServicioResponse>> response) {
                onCallTasa(response.getData());
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_not_found.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onCallTasa(ArrayList<ServicioResponse> servicios){
        Call<ResponseClient<AjusteResponse>> callDivisa = RetrofitClient.getInstance().getRequestInterface().getAjuste(
                "DIVISA"
        );
        callDivisa.enqueue(new ResponseCallback<ResponseClient<AjusteResponse>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<AjusteResponse> response) {
                recycler_servicio.setAdapter(new ServiciosAdminAdapter(
                        AdministrarServiciosFragment.this,
                        servicios,
                        Float.parseFloat(response.getData().getValue())
                ));
                recycler_servicio.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_servicio.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recycler_servicio.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        onCallData();
    }
}