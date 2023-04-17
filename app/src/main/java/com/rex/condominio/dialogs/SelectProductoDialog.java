package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.ProductosAdminAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class SelectProductoDialog extends Dialog {

    private LottieAnimationView animationView;
    private RecyclerView recycler_productos;
    private View view_not_found;
    private OnClickResponse<ProductoResponse> onClickResponse;
    private ArrayList<ProductoCompraRequest> data;

    public SelectProductoDialog(@NonNull Context context, ArrayList<ProductoCompraRequest> data,OnClickResponse<ProductoResponse> onClickResponse) {
        super(context);
        this.onClickResponse = onClickResponse;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_select_producto);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setWindowAnimations(R.style.dialogTranslationRight);

        animationView = findViewById(R.id.animationView);
        recycler_productos = findViewById(R.id.recycler_productos);
        view_not_found = findViewById(R.id.view_not_found);

        call();
    }

    private void call(){
        Call<ResponseClient<ArrayList<ProductoResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getAdminProductos(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ProductoResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_not_found.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ProductoResponse>> response) {
                ArrayList<ProductoResponse> newList = new ArrayList<>();
                for (ProductoResponse producto : response.getData()){
                    boolean result = true;
                    for (ProductoCompraRequest productoCompra : data){
                        if (producto.getIdPro() == productoCompra.getProducto().getIdPro()){
                            result = false;
                            break;
                        }
                    }
                    if (result) newList.add(producto);
                }

                recycler_productos.setAdapter(new ProductosAdminAdapter(getContext(), newList, new OnClickResponse<ProductoResponse>() {
                    @Override
                    public void onClick(ProductoResponse object) {
                        dismiss();
                        onClickResponse.onClick(object);
                    }
                }));
                recycler_productos.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_productos.setVisibility(View.VISIBLE);
            }
        });
    }
}
