package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.FamiliaResponse;

import java.util.ArrayList;
import java.util.List;

public class FamiliasAdapter extends RecyclerView.Adapter<FamiliasAdapter.ViewHolder> {

    private List<FamiliaResponse> data;

    public FamiliasAdapter(List<FamiliaResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_familiar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_apellido.setText(data.get(position).getDescFam());
        holder.tv_direccion.setText(data.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_apellido, tv_direccion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_apellido = itemView.findViewById(R.id.tv_apellido);
            tv_direccion = itemView.findViewById(R.id.tv_direccion);
        }
    }
}
