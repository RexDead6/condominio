package com.rex.condominio.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.CrearAnuncioActivity;
import com.rex.condominio.activities.ServicioActivity;
import com.rex.condominio.adapters.ServiciosPorPagarAdapter;
import com.rex.condominio.fragments.servicios.PagarServicioFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class ServiciosFragment extends Fragment {

    private RecyclerView recycler_servicios;
    private LottieAnimationView animationView;
    private FloatingActionButton floatingButtom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_servicios, container, false);

        recycler_servicios = v.findViewById(R.id.recycler_servicios);
        animationView = v.findViewById(R.id.animationView);
        floatingButtom = v.findViewById(R.id.floatingButtom);

        floatingButtom.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ServicioActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    floatingButtom,
                    "servicio"
            );
            startActivity(intent, options.toBundle());
        });

        Call<ResponseClient<ArrayList<ServicioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getServicio(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ServicioResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
                recycler_servicios.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ServicioResponse>> response) {
                recycler_servicios.setAdapter(new ServiciosPorPagarAdapter(response.getData(), new OnClickResponse<ServicioResponse>() {
                    @Override
                    public void onClick(ServicioResponse object) {
                        SupportPreferences.loadFrament(new PagarServicioFragment(object), getActivity().getSupportFragmentManager().beginTransaction(), true, R.id.fragment_container);
                    }
                }));
                recycler_servicios.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        return v;
    }
}