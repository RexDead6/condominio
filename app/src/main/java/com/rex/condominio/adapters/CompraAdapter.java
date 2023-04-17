package com.rex.condominio.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.PDFActivity;
import com.rex.condominio.dialogs.CompraDetalleDialog;
import com.rex.condominio.retrofit.response.CompraResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CompraResponse> data;
    private OnClickResponse<CompraResponse> onClickResponse;

    public CompraAdapter(Context context, ArrayList<CompraResponse> data, OnClickResponse<CompraResponse> onClickResponse) {
        this.context = context;
        this.data = data;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public CompraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraAdapter.ViewHolder holder, int position) {
        holder.tv_numero.setText(data.get(position).getIdCom()+"");
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMonto()));
        holder.tv_rif.setText(data.get(position).getProveedor().getRIF());
        holder.tv_proveedor.setText(data.get(position).getProveedor().getNomProv());
        holder.tv_porcenjate.setText(data.get(position).getPorcentaje()+"%");
        holder.btn_detalles.setOnClickListener(V-> {
            CompraDetalleDialog dialog = new CompraDetalleDialog(context, data.get(position).getProductos());
            dialog.show();
        });
        holder.btn_reporte.setOnClickListener(V -> {
            onClickResponse.onClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_numero, tv_rif, tv_proveedor, tv_porcenjate, tv_monto;
        private MaterialButton btn_detalles, btn_reporte;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_numero = itemView.findViewById(R.id.tv_numero);
            tv_rif = itemView.findViewById(R.id.tv_rif);
            tv_proveedor = itemView.findViewById(R.id.tv_proveedor);
            tv_porcenjate = itemView.findViewById(R.id.tv_porcenjate);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            btn_detalles = itemView.findViewById(R.id.btn_detalles);
            btn_reporte = itemView.findViewById(R.id.btn_reporte);
        }
    }
}
