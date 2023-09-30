package com.rex.condominio.fragments.familia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.FamiliaActivity;
import com.rex.condominio.adapters.FamiliasAdapter;
import com.rex.condominio.dialogs.CrearFamiliaDialog;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListaFamiliaFragment extends Fragment {

    private RecyclerView recycler_familias;
    private MaterialButton btn_crear_familia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*setEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.enter_slide));
        setExitTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.exit_fade));*/
        View v = inflater.inflate(R.layout.fragment_lista_familia, container, false);

        recycler_familias = v.findViewById(R.id.recycler_familias);
        btn_crear_familia = v.findViewById(R.id.btn_crear_familia);

        recycler_familias.setAdapter(new FamiliasAdapter(
                new SupportPreferences.ObjectPreference<FamiliaResponse>().getObject(getContext(), SupportPreferences.FAMILIA_LIST_OBJECT, FamiliaResponse[].class)
        ));
        recycler_familias.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_crear_familia.setOnClickListener(V -> {
            CrearFamiliaDialog dialog = new CrearFamiliaDialog(getContext(), SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_PREFERENCE) ,new OnClickResponse() {
                @Override
                public void onClick(Object object) {
                    reloadView();
                }
            });
            dialog.show();
        });

        return v;
    }

    public void reloadView(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        Call<ResponseClient<ArrayList<FamiliaResponse>>> getAll = RetrofitClient.getInstance().getRequestInterface().getAllFamilia(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        getAll.enqueue(new ResponseCallback<ResponseClient<ArrayList<FamiliaResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<FamiliaResponse>> response) {
                new SupportPreferences.ObjectPreference<FamiliaResponse>().saveObject(
                        getContext(),
                        response.getData(),
                        SupportPreferences.FAMILIA_LIST_OBJECT
                );
                recycler_familias.setAdapter(new FamiliasAdapter((List<FamiliaResponse>) response.getData()));
                recycler_familias.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}