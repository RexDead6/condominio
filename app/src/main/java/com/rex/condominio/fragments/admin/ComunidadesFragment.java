package com.rex.condominio.fragments.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ComunidadAdapter;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ComunidadResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ComunidadesFragment extends Fragment {

    private Spinner spin_comunidad;
    private RecyclerView recycler_comunidades;
    private ProgressDialog progressDialog;
    private MaterialButton btn_registrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comunidades, container, false);

        spin_comunidad = v.findViewById(R.id.spin_comunidad);
        recycler_comunidades = v.findViewById(R.id.recycler_comunidades);
        progressDialog = new ProgressDialog(getContext());
        btn_registrar = v.findViewById(R.id.btn_registrar);

        spin_comunidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ComunidadResponse comunidad = (ComunidadResponse)spin_comunidad.getSelectedItem();
                SupportPreferences.getInstance(getContext()).savePreferenceInt(
                        SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE,
                        comunidad.getIdUrb()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        call_api();

        btn_registrar.setOnClickListener(V -> {
            this.dialogCreate();
        });

        return v;
    }

    public void call_api(){
        progressDialog.show();
        Call<ResponseClient<ArrayList<ComunidadResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getComunidadesAdmin();
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ComunidadResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ComunidadResponse>> response) {
                spin_comunidad.setAdapter(new ArrayAdapter<ComunidadResponse>(
                        getContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        response.getData()
                ));

                int idUrb = SupportPreferences.getInstance(getContext()).getPreferenceInt(
                        SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE
                );

                for (int i = 0; i<response.getData().size(); i++) {
                    if (response.getData().get(i).getIdUrb() == idUrb)
                        spin_comunidad.setSelection(i);
                }

                recycler_comunidades.setAdapter(new ComunidadAdapter(response.getData()));
                recycler_comunidades.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    private void dialogCreate(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.modal_comunidad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialog.findViewById(R.id.btn_register).setOnClickListener(V -> {
            EditText et_nombre = dialog.findViewById(R.id.et_nombre);
            EditText et_direccion = dialog.findViewById(R.id.et_direccion);

            if (et_nombre.getText().toString().isEmpty()) {
                et_nombre.setError("Debe ingresar un nombre");
                return;
            }

            if (et_direccion.getText().toString().isEmpty()){
                et_direccion.setError("Debe ingresar una direccion");
                return;
            }

            ComunidadResponse comunidad = new ComunidadResponse();
            comunidad.setNomUrb(et_nombre.getText().toString());
            comunidad.setDireccion(et_direccion.getText().toString());

            progressDialog.show();

            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertComunidad(
                    SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                    comunidad
            );
            call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                @Override
                public Context returnContext() {
                    return getContext();
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                }

                @Override
                public void doCallBackResponse(ResponseClient<Object> response) {
                    new AlertDialog.Builder(getContext())
                            .setMessage(response.getMessage())
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    call_api();
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            });
        });
        dialog.show();
    }
}