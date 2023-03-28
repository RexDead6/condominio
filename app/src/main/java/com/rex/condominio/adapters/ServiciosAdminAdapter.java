package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class ServiciosAdminAdapter extends RecyclerView.Adapter<ServiciosAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServicioResponse> data;
    private ArrayList<ServicioResponse> dataFiltered;

    public ServiciosAdminAdapter(Context context, ArrayList<ServicioResponse> data) {
        this.context = context;
        this.data = data;
        this.dataFiltered = data;
    }

    @NonNull
    @Override
    public ServiciosAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosAdminAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(data.get(position).getDescSer());
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer()));
        holder.tv_estado.setText((data.get(position).getStatusSer() == 1) ? "Activo" : "Inactivo");
        holder.tv_tipo.setText((data.get(position).getIsMensualSer() == 1) ? "Mensual" : "Pago unico");
        holder.tv_fecha.setText(data.get(position).getFechaInicioServicio());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_desc, tv_monto, tv_tipo, tv_estado, tv_fecha;
        private Button btn_editar, btn_pmv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            tv_tipo = itemView.findViewById(R.id.tv_tipo);
            tv_estado = itemView.findViewById(R.id.tv_estado);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
        }
    }
}
