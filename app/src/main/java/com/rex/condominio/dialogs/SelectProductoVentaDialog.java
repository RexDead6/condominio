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

public class SelectProductoVentaDialog extends Dialog {

    private LottieAnimationView animationView;
    private RecyclerView recycler_productos;
    private View view_not_found;
    private OnClickResponse<ProductoResponse> onClickResponse;
    private ArrayList<ProductoResponse> data;

    public SelectProductoVentaDialog(@NonNull Context context, ArrayList<ProductoResponse> data, OnClickResponse<ProductoResponse> onClickResponse) {
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

        recycler_productos.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.GONE);
        recycler_productos.setAdapter(new ProductosAdminAdapter(getContext(), data, new OnClickResponse<ProductoResponse>() {
            @Override
            public void onClick(ProductoResponse object) {
                dismiss();
                onClickResponse.onClick(object);
            }
        }));
        recycler_productos.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
