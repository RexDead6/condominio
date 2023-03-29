package com.rex.condominio.fragments.servicios;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.dialogs.SelectPagoMovilDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.ServicioRequest;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CrearServicioFragment extends Fragment {

    private TextInputEditText et_descripcion, et_monto, et_pago_movil;
    private RadioGroup radio_group;
    private RadioButton radio_mensual, radio_unico;
    private Button btn_registrar;

    private int idPmv;
    private int isMensual;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_servicio, container, false);

        et_pago_movil = v.findViewById(R.id.et_pago_movil);
        et_descripcion = v.findViewById(R.id.et_descripcion);
        radio_group = v.findViewById(R.id.radio_group);
        radio_mensual = v.findViewById(R.id.radio_mensual);
        radio_unico = v.findViewById(R.id.radio_unico);
        et_monto = v.findViewById(R.id.et_monto);
        btn_registrar = v.findViewById(R.id.btn_registrar);

        radio_group.setOnCheckedChangeListener(
                (RadioGroup, i) -> isMensual = ((RadioButton) v.findViewById(i)).getText().toString().equals("Mensual") ? 1 : 0
        );

        et_pago_movil.setOnClickListener(V -> {
            SelectPagoMovilDialog dialog = new SelectPagoMovilDialog(getContext(), new OnClickResponse<PagoMovilResponse>() {
                @Override
                public void onClick(PagoMovilResponse object) {
                    et_pago_movil.setText(object.getCedPmv() + " - " +object.getTelPmv());
                    idPmv = object.getIdPmv();
                }
            });
            dialog.show();
        });

        btn_registrar.setOnClickListener(V -> {
            if (!validateInputs()) return;

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();

            ServicioRequest servicioRequest = new ServicioRequest(
                    idPmv,
                    et_descripcion.getText().toString().trim(),
                    isMensual,
                    Double.parseDouble(et_monto.getText().toString())
            );

            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertServicio(
                    SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                    servicioRequest
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
                    Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            });
        });

        return v;
    }

    private boolean validateInputs(){
        boolean success = true;

        if (et_descripcion.getText().toString().isEmpty()){
            et_descripcion.setError("Ingrese la descripción");
            success = false;
        }

        if (!radio_mensual.isChecked() && !radio_unico.isChecked()){
            radio_mensual.setError("Seleccione un genero");
            success = false;
        }

        if (et_monto.getText().toString().isEmpty()){
            et_monto.setError("Ingrese el monto");
            success = false;
        }

        if (et_pago_movil.getText().toString().isEmpty()){
            et_pago_movil.setError("Ingrese un Pago movil");
            success = false;
        }

        return success;
    }
}