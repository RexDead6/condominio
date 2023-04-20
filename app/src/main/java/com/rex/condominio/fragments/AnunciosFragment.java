package com.rex.condominio.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
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

        if (!new TokenSupport(getContext()).getIdRol().equals("1")){
            floatingButtom.setVisibility(View.GONE);
        }

        callRequest();
        return v;
    }

    private void callRequest(){
        Call<ResponseClient<ArrayList<AnuncioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getAnuncios(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new Callback<ResponseClient<ArrayList<AnuncioResponse>>>() {
            @Override
            public void onResponse(Call<ResponseClient<ArrayList<AnuncioResponse>>> call, Response<ResponseClient<ArrayList<AnuncioResponse>>> response) {
                if (response.code() == 200) {
                    recycler_anuncios.setAdapter(new AnunciosAdapter(response.body().getData(), getContext()));
                    recycler_anuncios.setLayoutManager(new LinearLayoutManager(getContext()));
                    return;
                }

                ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

                new AlertDialog.Builder(getContext())
                        .setMessage(errorResponse.getMessage())
                        .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                        .create().show();
            }

            @Override
            public void onFailure(Call<ResponseClient<ArrayList<AnuncioResponse>>> call, Throwable t) {
                Log.e("getAnuncios", t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callRequest();
    }
}