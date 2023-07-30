package com.rex.condominio.fragments.servicios;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rex.condominio.R;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.dialogs.SelectPagoMovilDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.ServicioRequest;
import com.rex.condominio.retrofit.response.AjusteResponse;
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

    private TextInputLayout layout_input;
    private TextInputEditText et_descripcion, et_monto, et_pago_movil, et_monto_bs;
    private RadioGroup radio_group, radio_group_divisa;
    private RadioButton radio_mensual, radio_unico, radio_bs, radio_divisa;
    private Button btn_registrar;
    private int idPmv;
    private int isMensual;
    private int divisa;
    private float tasa = 0.0f;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_servicio, container, false);

        et_pago_movil = v.findViewById(R.id.et_pago_movil);
        et_descripcion = v.findViewById(R.id.et_descripcion);
        radio_group = v.findViewById(R.id.radio_group);
        radio_mensual = v.findViewById(R.id.radio_mensual);
        radio_unico = v.findViewById(R.id.radio_unico);
        radio_group_divisa = v.findViewById(R.id.radio_group_divisa);
        radio_bs = v.findViewById(R.id.radio_bs);
        radio_divisa = v.findViewById(R.id.radio_divisa);
        et_monto = v.findViewById(R.id.et_monto);
        et_monto_bs = v.findViewById(R.id.et_monto_bs);
        layout_input = v.findViewById(R.id.layout_input);
        btn_registrar = v.findViewById(R.id.btn_registrar);

        Call<ResponseClient<AjusteResponse>> callDivisa = RetrofitClient.getInstance().getRequestInterface().getAjuste(
                "DIVISA"
        );

        ProgressDialog progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.show();
        callDivisa.enqueue(new ResponseCallback<ResponseClient<AjusteResponse>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                progressDialog1.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<AjusteResponse> response) {
                tasa = Float.parseFloat(response.getData().getValue());
            }
        });

        radio_group.setOnCheckedChangeListener(
                (RadioGroup, i) -> isMensual = ((RadioButton) v.findViewById(i)).getText().toString().equals("Mensual") ? 1 : 0
        );

        radio_group_divisa.setOnCheckedChangeListener(
                (RadioGroup, i) -> {
                    layout_input.setVisibility(i == R.id.radio_bs ? View.GONE : View.VISIBLE);
                    divisa = i == R.id.radio_bs ? 0 : 1;
                }
        );

        et_monto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    et_monto_bs.setText(SupportPreferences.formatCurrency(
                            Float.parseFloat(et_monto.getText().toString()) * tasa
                    ));
                }catch (Exception e){
                    et_monto_bs.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                    Double.parseDouble(et_monto.getText().toString()),
                    divisa
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

        if (!radio_bs.isChecked() && !radio_divisa.isChecked()) {
            radio_bs.setError("Seleccione una moneda de cobro");
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