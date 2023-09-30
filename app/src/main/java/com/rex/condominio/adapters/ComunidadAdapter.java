package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.ComunidadResponse;

import java.util.ArrayList;

public class ComunidadAdapter extends RecyclerView.Adapter<ComunidadAdapter.ViewHolder> {

    private ArrayList<ComunidadResponse> data;

    public ComunidadAdapter(ArrayList<ComunidadResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ComunidadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comunidad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComunidadAdapter.ViewHolder holder, int position) {
        holder.tv_comunidad.setText(data.get(position).getNomUrb());
        holder.tv_cantidad_familias.setText("Familias: "+data.get(position).getTotalFamilias());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_comunidad, tv_cantidad_familias;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_comunidad = itemView.findViewById(R.id.tv_comunidad);
            tv_cantidad_familias = itemView.findViewById(R.id.tv_cantidad_familias);
        }
    }
}
