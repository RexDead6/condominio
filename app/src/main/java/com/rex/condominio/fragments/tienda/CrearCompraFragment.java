package com.rex.condominio.fragments.tienda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.activities.tienda.ProveedorActivity;
import com.rex.condominio.dialogs.ProductoCompraDialog;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.CompraRequest;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.retrofit.response.ProveedorResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CrearCompraFragment extends Fragment {

    private MaterialButton btn_productos, btn_registrar;
    private TextInputEditText et_proveedor, et_porcentaje;
    private int idProv;
    private ArrayList<ProductoCompraRequest> productoCompra = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_compra, container, false);

        et_proveedor = v.findViewById(R.id.et_proveedor);
        et_porcentaje = v.findViewById(R.id.et_porcentaje);
        btn_productos = v.findViewById(R.id.btn_productos);
        btn_registrar = v.findViewById(R.id.btn_registrar);

        et_proveedor.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProveedorActivity.class);
            startActivityForResult(intent, 3);
        });

        btn_productos.setOnClickListener(V -> {
            ProductoCompraDialog dialog = new ProductoCompraDialog(
                    getContext(),
                    productoCompra,
                    et_porcentaje.getText().toString().isEmpty() ? 0 : Float.parseFloat(et_porcentaje.getText().toString()),
                    new OnClickResponse<ArrayList<ProductoCompraRequest>>() {
                        @Override
                        public void onClick(ArrayList<ProductoCompraRequest> object) {
                            productoCompra = object;
                        }
                    }
            );
            dialog.show();
        });

        btn_registrar.setOnClickListener(V -> {
            if (!validateInputs()) return;

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();

            float total = 0;

            for(ProductoCompraRequest producto : productoCompra){
                total += producto.getCosto() * producto.getCantidad();
            }

            CompraRequest compraRequest = new CompraRequest(
                    idProv,
                    total,
                    Float.parseFloat(et_porcentaje.getText().toString()),
                    productoCompra
            );

            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertCompra(
                    SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                    compraRequest
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
                    getActivity().onBackPressed();
                }
            });
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3){
            if (data.getExtras().getSerializable("proveedor") != null) {
                ProveedorResponse proveedor = (ProveedorResponse) data.getExtras().getSerializable("proveedor");
                idProv = proveedor.getIdProv();
                et_proveedor.setText(proveedor.getNomProv());
            }
        }
    }

    private boolean validateInputs(){
        boolean SUCCESS = true;

        if (et_proveedor.getText().toString().isEmpty()) {
            et_proveedor.setError("Ingrese un proveedor");
            SUCCESS = false;
        }

        if (et_porcentaje.getText().toString().isEmpty()){
            et_porcentaje.setError("Ingrese su porcentaje de aumento");
            SUCCESS = false;
        }

        if (productoCompra.isEmpty()){
            new AlertDialog.Builder(getContext())
                    .setMessage("Debe ingresar al menos un producto para continuar")
                    .setPositiveButton("Aceptar", (d,w) -> {d.dismiss();})
                    .create().show();
            SUCCESS = false;
        }

        return SUCCESS;
    }
}