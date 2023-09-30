package com.rex.condominio.fragments.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.adapters.FamiliasAdapter;
import com.rex.condominio.dialogs.CrearFamiliaDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FamiliasFragment extends Fragment {

    private RecyclerView recycler_familias;
    private View not_found;
    private LottieAnimationView animationView;
    private MaterialButton btn_crear_familia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_familias, container, false);

        recycler_familias = v.findViewById(R.id.recycler_familias);
        not_found = v.findViewById(R.id.not_found);
        animationView = v.findViewById(R.id.animationView);
        btn_crear_familia = v.findViewById(R.id.btn_crear_familia);

        btn_crear_familia.setOnClickListener(V -> {
            CrearFamiliaDialog dialog = new CrearFamiliaDialog(getContext(), SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE), new OnClickResponse() {
                @Override
                public void onClick(Object object) {
                    callApi();
                }
            });
            dialog.show();
        });

        callApi();

        return v;
    }

    private void callApi(){
        animationView.setVisibility(View.VISIBLE);
        recycler_familias.setVisibility(View.GONE);
        not_found.setVisibility(View.GONE);
        Call<ResponseClient<ArrayList<FamiliaResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getFamByComunidad(
                ""+SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE)
        );

        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<FamiliaResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<FamiliaResponse>> response) {
                recycler_familias.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_familias.setVisibility(View.VISIBLE);
                recycler_familias.setAdapter(new FamiliasAdapter(response.getData()));
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                not_found.setVisibility(View.VISIBLE);
            }
        });
    }
}