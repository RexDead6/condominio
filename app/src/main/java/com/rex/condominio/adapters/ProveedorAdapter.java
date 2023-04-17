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
import com.rex.condominio.retrofit.response.ProveedorResponse;
import com.rex.condominio.utils.OnClickResponse;

import java.util.ArrayList;

public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ViewHolder> {

    private ArrayList<ProveedorResponse> data;
    private OnClickResponse<ProveedorResponse> onClickResponse;

    public ProveedorAdapter(ArrayList<ProveedorResponse> data, OnClickResponse<ProveedorResponse> onClickResponse) {
        this.data = data;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public ProveedorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedor, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorAdapter.ViewHolder holder, int position) {
        holder.tv_nombre.setText(data.get(position).getNomProv());
        holder.tv_rif.setText(data.get(position).getRIF());
        holder.btn_pagar.setOnClickListener(V -> onClickResponse.onClick(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nombre, tv_rif;
        private MaterialButton btn_pagar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_rif = itemView.findViewById(R.id.tv_rif);
            btn_pagar = itemView.findViewById(R.id.btn_pagar);
        }
    }
}
