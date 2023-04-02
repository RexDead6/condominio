package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.FacturaResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TipoFactura;

import java.util.ArrayList;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.ViewHolder> {

    private ArrayList<FacturaResponse> data;

    public FacturaAdapter(ArrayList<FacturaResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public FacturaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factura, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaAdapter.ViewHolder holder, int position) {
        holder.tv_id.setText(data.get(position).getIdFac()+"");
        holder.tv_servicio.setText(data.get(position).getServicio().getDescSer());
        holder.tv_meses.setText(data.get(position).getMeses()+"");
        holder.tv_fecha.setText(SupportPreferences.formatDate(data.get(position).getFechapagFac()));
        holder.tv_ref.setText(data.get(position).getPagos().get(0).getRefPag());
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoFac()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id, tv_servicio, tv_meses, tv_fecha, tv_ref, tv_monto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_servicio = itemView.findViewById(R.id.tv_servicio);
            tv_meses = itemView.findViewById(R.id.tv_meses);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_ref = itemView.findViewById(R.id.tv_ref);
            tv_monto = itemView.findViewById(R.id.tv_monto);
        }
    }
}
