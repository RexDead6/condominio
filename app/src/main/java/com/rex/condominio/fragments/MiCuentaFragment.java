package com.rex.condominio.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.FamiliaActivity;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.activities.PagoMovilActivity;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;


public class MiCuentaFragment extends Fragment {

    private FloatingActionButton btn_pago_movil, btn_familia, btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_mi_cuenta, container, false);

        btn_pago_movil = v.findViewById(R.id.btn_pago_movil);
        btn_pago_movil.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), PagoMovilActivity.class);
            startActivity(intent);
        });

        btn_familia = v.findViewById(R.id.btn_familia);
        btn_familia.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), FamiliaActivity.class);
            startActivity(intent);
        });

        btn_logout = v.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(V -> {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.show();
            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().logout(
                    SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
            );
            call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                @Override
                public Context returnContext() {
                    return getContext();
                }

                @Override
                public void onFinish() {
                    dialog.dismiss();
                }

                @Override
                public void doCallBackResponse(ResponseClient<Object> response) {
                    SupportPreferences.getInstance(getContext()).savePreference(SupportPreferences.TOKEN_PREFERENCE, "");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        });

        return v;
    }
}