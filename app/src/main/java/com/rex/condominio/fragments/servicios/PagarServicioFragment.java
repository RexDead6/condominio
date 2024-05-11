package com.rex.condominio.fragments.servicios;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ServiciosPorPagarAdapter;
import com.rex.condominio.dialogs.PagarServicioDialog;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.PagarServicioRequest;
import com.rex.condominio.retrofit.request.PagosServiciosRequest;
import com.rex.condominio.retrofit.response.AjusteResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PagarServicioFragment extends Fragment {

    private ServicioResponse servicio;
    private TextInputEditText et_descripcion, et_monto, et_cedula, et_telefono, et_banco;
    private ProgressDialog progressDialog;
    private MaterialButton btn_registrar;
    private float monto_total = 0;

    public PagarServicioFragment(ServicioResponse servicio){
        this.servicio = servicio;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pagar_servicio, container, false);

        progressDialog = new ProgressDialog(getContext());
        et_descripcion = v.findViewById(R.id.et_descripcion);
        et_monto = v.findViewById(R.id.et_monto);
        et_cedula = v.findViewById(R.id.et_cedula);
        et_telefono = v.findViewById(R.id.et_telefono);
        et_banco = v.findViewById(R.id.et_banco);
        btn_registrar = v.findViewById(R.id.btn_registrar);

        if (servicio.getDivisa() == 1) {
            progressDialog.show();
            callTasa();
        } else {
            et_monto.setText(SupportPreferences.formatCurrency(servicio.getMontoSer() * servicio.getMesesPorPagar()));
            monto_total = (servicio.getMontoSer() * servicio.getMesesPorPagar());
        }

        et_descripcion.setText(servicio.getDescSer());
        et_cedula.setText(servicio.getPagoMovil().getCedPmv());
        et_telefono.setText(servicio.getPagoMovil().getTelPmv());
        et_banco.setText(servicio.getPagoMovil().getBanco().getNomBan());
        btn_registrar.setOnClickListener(V -> {
            PagarServicioDialog dialog = new PagarServicioDialog(getContext());
            dialog.setContentView(R.layout.modal_pagar_servicio);
            dialog.findViewById(R.id.btn_pagar).setOnClickListener(V1 -> {
                EditText et_ref = dialog.findViewById(R.id.et_ref);
                if (et_ref.getText().toString().isEmpty()){
                    et_ref.setError("Ingrese su referencia bancaria");
                    return;
                }

                dialog.dismiss();

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.show();

                ArrayList<PagosServiciosRequest> pagos = new ArrayList<>();
                pagos.add(new PagosServiciosRequest(
                        "Pago Movil",
                        et_ref.getText().toString().trim(),
                        monto_total
                ));

                PagarServicioRequest pagarServicioRequest = new PagarServicioRequest(
                        servicio.getIdSer(),
                        monto_total,
                        pagos,
                        servicio.getMesesPorPagar()
                );
                Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertPagoServicio(
                        SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                        pagarServicioRequest
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
            dialog.show();
        });

        return v;
    }

    private void callTasa(){
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
                progressDialog.dismiss();
            }

            @Override
            public void doCallBackResponse(ResponseClient<AjusteResponse> response) {
                monto_total = (servicio.getMontoSer() * servicio.getMesesPorPagar()) * Float.parseFloat(response.getData().getValue());
                et_monto.setText(SupportPreferences.formatCurrency(monto_total));
            }
        });
    }
}