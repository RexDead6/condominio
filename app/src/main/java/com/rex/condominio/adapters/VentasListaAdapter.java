package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.VentaResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.util.ArrayList;

public class VentasListaAdapter extends RecyclerView.Adapter<VentasListaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VentaResponse> data;
    private String type;
    private String status;
    private OnClickResponse<String[]> onClickResponse;
    private OnClickResponse<VentaResponse> onClickResponseReporte;

    public VentasListaAdapter(Context context, ArrayList<VentaResponse> data, String type, String status, OnClickResponse<String[]> onClickResponse, OnClickResponse<VentaResponse> onClickResponseReporte) {
        this.context = context;
        this.data = data;
        this.type = type;
        this.status = status;
        this.onClickResponse = onClickResponse;
        this.onClickResponseReporte = onClickResponseReporte;
    }

    @NonNull
    @Override
    public VentasListaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VentasListaAdapter.ViewHolder holder, int position) {
        holder.tv_numero.setText(data.get(position).getIdVen()+"");
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoVen()));
        holder.tv_ref.setText(data.get(position).getPagos().get(0).getRefPag());
        holder.btn_pagado.setOnClickListener(V -> onClickResponse.onClick(new String[]{ data.get(position).getIdVen()+"", "2" }));
        holder.btn_recibido.setOnClickListener(V -> onClickResponse.onClick(new String[]{ data.get(position).getIdVen()+"", "3" }));
        holder.btn_reporte.setOnClickListener(V -> onClickResponseReporte.onClick(data.get(position)));

        if (type.equals("cliente") && status.equals("2")){
            holder.btn_recibido.setVisibility(View.VISIBLE);
        }

        if (type.equals("vendedor") && status.equals("1")){
            holder.btn_pagado.setVisibility(View.VISIBLE);
        }

        if (status.equals("3")){
            holder.btn_reporte.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_numero, tv_ci, tv_usuario, tv_ref, tv_monto;
        private MaterialButton btn_pagado, btn_recibido, btn_reporte;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_numero = itemView.findViewById(R.id.tv_numero);
            tv_ci = itemView.findViewById(R.id.tv_ci);
            tv_usuario = itemView.findViewById(R.id.tv_usuario);
            tv_ref = itemView.findViewById(R.id.tv_ref);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            btn_pagado = itemView.findViewById(R.id.btn_pagado);
            btn_recibido = itemView.findViewById(R.id.btn_recibido);
            btn_reporte = itemView.findViewById(R.id.btn_reporte);
        }
    }
}
