package com.rex.condominio.activities.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ProductosVentaAdapter;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.dialogs.SelectProductoVentaDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.PagoVentaRequest;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.retrofit.request.VentaRequest;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.retrofit.response.ProveedorResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class VentaActivity extends AppCompatActivity {

    private UsuarioResponse usuarioVendedor;
    private RecyclerView recycler_productos;
    private ProductosVentaAdapter adapter;
    private MaterialButton btn_pagar, btn_productos;
    private ArrayList<ProductoResponse> Productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        if (getIntent().getExtras().getSerializable("vendedor") == null) {
            onBackPressed();
        }
        usuarioVendedor = (UsuarioResponse) getIntent().getExtras().getSerializable("vendedor");

        adapter = new ProductosVentaAdapter(this, Productos);

        recycler_productos = findViewById(R.id.recycler_productos);
        btn_pagar = findViewById(R.id.btn_pagar);
        btn_productos = findViewById(R.id.btn_productos);

        recycler_productos.setAdapter(adapter);
        recycler_productos.setLayoutManager(new LinearLayoutManager(this));

        btn_productos.setOnClickListener(V -> {
            SelectProductoVentaDialog dialog = new SelectProductoVentaDialog(this, usuarioVendedor.getProductos(), new OnClickResponse<ProductoResponse>() {
                @Override
                public void onClick(ProductoResponse object) {
                    Productos.add(object);
                    adapter = new ProductosVentaAdapter(VentaActivity.this, Productos);
                    recycler_productos.setAdapter(adapter);
                    recycler_productos.setLayoutManager(new LinearLayoutManager(VentaActivity.this));
                }
            });
            dialog.show();
        });

        btn_pagar.setOnClickListener(V -> {
            ArrayList<ProductoCompraRequest> productos = adapter.getProductos();
            if (productos.isEmpty()){
                new AlertDialog.Builder(this)
                        .setMessage("Ingrese al menos un poducto")
                        .setPositiveButton("Aceptar", (d, w) -> d.dismiss())
                        .create().show();
                return;
            }

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            Call<ResponseClient<PagoMovilResponse>> getCurrenPvm = RetrofitClient.getInstance().getRequestInterface().getCurrentPvm(
                    usuarioVendedor.getIdUsu()+""
            );
            getCurrenPvm.enqueue(new ResponseCallback<ResponseClient<PagoMovilResponse>>() {
                @Override
                public Context returnContext() {
                    return VentaActivity.this;
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                }

                @Override
                public void doCallBackResponse(ResponseClient<PagoMovilResponse> response) {
                    BottomSheetDialog dialog = new BottomSheetDialog(VentaActivity.this);
                    dialog.setContentView(R.layout.modal_pagar_venta);
                    ((TextInputEditText) dialog.findViewById(R.id.et_cedula)).setText(response.getData().getCedPmv());
                    ((TextInputEditText) dialog.findViewById(R.id.et_telefono)).setText(response.getData().getTelPmv());
                    ((TextInputEditText) dialog.findViewById(R.id.et_banco)).setText(response.getData().getBanco().getCodBan() + " - "+response.getData().getBanco().getNomBan());

                    float monto = 0;
                    for (ProductoCompraRequest producto : productos){
                        monto += producto.getCantidad() * producto.getProducto().getCostoPro();
                    }

                    ((TextView) dialog.findViewById(R.id.tv_total)).setText(SupportPreferences.formatCurrency(monto));

                    float finalMonto = monto;
                    dialog.findViewById(R.id.btn_pagar).setOnClickListener(V -> {
                        TextInputEditText et_ref = dialog.findViewById(R.id.et_ref);
                        if (et_ref.getText().toString().isEmpty()){
                            et_ref.setError("Ingrese su referencia");
                            return;
                        }

                        VentaRequest venta = new VentaRequest();
                        venta.setIdVenUsu(usuarioVendedor.getIdUsu());
                        venta.setProductosVenta(productos);
                        venta.setMonto(finalMonto);
                        ArrayList<PagoVentaRequest> pagos = new ArrayList<>();
                        pagos.add(new PagoVentaRequest("Pago Movil", et_ref.getText().toString(), finalMonto));
                        venta.setPagos(pagos);

                        Call<ResponseClient<Object>> callVenta = RetrofitClient.getInstance().getRequestInterface().insertVenta(
                                SupportPreferences.getInstance(VentaActivity.this).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                venta
                        );
                        callVenta.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                            @Override
                            public Context returnContext() {
                                return VentaActivity.this;
                            }

                            @Override
                            public void doCallBackResponse(ResponseClient<Object> response) {
                                Toast.makeText(VentaActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                    });

                    dialog.show();
                }
            });
        });
    }
}