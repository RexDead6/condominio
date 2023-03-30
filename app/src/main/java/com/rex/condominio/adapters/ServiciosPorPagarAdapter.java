package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.fragments.servicios.PagarServicioFragment;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class ServiciosPorPagarAdapter extends RecyclerView.Adapter<ServiciosPorPagarAdapter.ViewHolder> {

    private ArrayList<ServicioResponse> data;
    private OnClickResponse<ServicioResponse> onClickResponse;

    public ServiciosPorPagarAdapter(ArrayList<ServicioResponse> data, OnClickResponse<ServicioResponse> onClickResponse) {
        this.data = data;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public ServiciosPorPagarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosPorPagarAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(data.get(position).getDescSer());
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer()));
        holder.tv_meses.setText(data.get(position).getIsMensualSer() == 0 ? "Pago unico" : data.get(position).getMesesPorPagar()+"");
        holder.tv_total.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer() * data.get(position).getMesesPorPagar()));
        holder.btn_pagar.setOnClickListener(V-> onClickResponse.onClick(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_desc, tv_monto, tv_meses, tv_total;
        private MaterialButton btn_pagar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            tv_meses = itemView.findViewById(R.id.tv_meses);
            tv_total = itemView.findViewById(R.id.tv_total);
            btn_pagar = itemView.findViewById(R.id.btn_pagar);
        }
    }
}
