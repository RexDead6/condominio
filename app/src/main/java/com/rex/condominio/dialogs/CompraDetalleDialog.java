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
import com.rex.condominio.adapters.CompraDetalleAdapter;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;

import java.util.ArrayList;

public class CompraDetalleDialog extends Dialog {

    private RecyclerView recycler_productos;
    ArrayList<ProductoCompraRequest> data;

    public CompraDetalleDialog(@NonNull Context context, ArrayList<ProductoCompraRequest> data) {
        super(context);
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_detalle_compra);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        recycler_productos = findViewById(R.id.recycler_productos);
        recycler_productos.setAdapter(new CompraDetalleAdapter(getContext(), data));
        recycler_productos.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
