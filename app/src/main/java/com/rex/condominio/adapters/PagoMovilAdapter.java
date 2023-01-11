package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.PagoMovilResponse;

import java.util.ArrayList;

public class PagoMovilAdapter extends RecyclerView.Adapter<PagoMovilAdapter.ViewHolder> {

    private ArrayList<PagoMovilResponse> data;

    public PagoMovilAdapter(ArrayList<PagoMovilResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PagoMovilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago_movil, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoMovilAdapter.ViewHolder holder, int position) {
        holder.tv_cedula.setText(data.get(position).getCedPmv());
        holder.tv_telefono.setText(data.get(position).getTelPmv());
        holder.tv_banco.setText(data.get(position).getBanco().getNomBan());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_cedula, tv_telefono, tv_banco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cedula = itemView.findViewById(R.id.tv_cedula);
            tv_telefono = itemView.findViewById(R.id.tv_telefono);
            tv_banco = itemView.findViewById(R.id.tv_banco);
        }
    }
}
