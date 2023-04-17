package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.adapters.CompraProductosAdapter;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.utils.OnClickResponse;

import java.util.ArrayList;

public class ProductoCompraDialog extends Dialog {

    private ArrayList<ProductoCompraRequest> data;
    private float porcentaje;
    private OnClickResponse<ArrayList<ProductoCompraRequest>> onClickResponse;

    private RecyclerView recycler_productos_compra;
    private MaterialButton btn_agregar, btn_cargar;
    private CompraProductosAdapter adapter;

    public ProductoCompraDialog(@NonNull Context context, ArrayList<ProductoCompraRequest> data, float porcentaje, OnClickResponse<ArrayList<ProductoCompraRequest>> onClickResponse) {
        super(context);
        this.data = data;
        this.porcentaje = porcentaje;
        this.onClickResponse = onClickResponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_productos_compra);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        recycler_productos_compra = findViewById(R.id.recycler_productos_compra);
        btn_agregar = findViewById(R.id.btn_agregar);
        btn_cargar = findViewById(R.id.btn_cargar);

        adapter = new CompraProductosAdapter(data, porcentaje);
        recycler_productos_compra.setAdapter(adapter);
        recycler_productos_compra.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_agregar.setOnClickListener(V -> {
            SelectProductoDialog dialog = new SelectProductoDialog(getContext(), adapter.getData(),new OnClickResponse<ProductoResponse>() {
                @Override
                public void onClick(ProductoResponse object) {
                    ArrayList<ProductoCompraRequest> productos = adapter.getData();
                    productos.add(new ProductoCompraRequest(object));
                    adapter = new CompraProductosAdapter(productos, porcentaje);
                    recycler_productos_compra.setAdapter(adapter);
                }
            });
            dialog.show();
        });
        btn_cargar.setOnClickListener(V -> {
            dismiss();
            onClickResponse.onClick(adapter.getData());
        });
    }
}
