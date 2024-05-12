package com.rex.condominio.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialElevationScale;
import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.CrearAnuncioActivity;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.activities.MainActivity;
import com.rex.condominio.adapters.AnunciosAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.AnuncioResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnunciosFragment extends Fragment {

    private RecyclerView recycler_anuncios;
    private FloatingActionButton floatingButtom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anuncios, container, false);
        /*setExitTransition(new MaterialElevationScale(true));
        setReenterTransition(new MaterialElevationScale(true));*/

        recycler_anuncios = v.findViewById(R.id.recycler_anuncios);
        floatingButtom = v.findViewById(R.id.floatingButtom);

        floatingButtom.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), CrearAnuncioActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    floatingButtom,
                    "crearAnuncio"
            );
            startActivity(intent, options.toBundle());
        });

        if (
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.ADMIN_COMUNIDAD_PREFERENCE) == "true"
                || Integer.parseInt(new TokenSupport(getContext()).getIdRol()) > 1
        ){
            floatingButtom.setVisibility(View.GONE);
        }

        callRequest();
        return v;
    }

    private void callRequest(){
        Call<ResponseClient<ArrayList<AnuncioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getAnuncios(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_PREFERENCE)+""
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<AnuncioResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<AnuncioResponse>> response) {
                recycler_anuncios.setAdapter(new AnunciosAdapter(response.getData(), getContext()));
                recycler_anuncios.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {}
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callRequest();
    }
}