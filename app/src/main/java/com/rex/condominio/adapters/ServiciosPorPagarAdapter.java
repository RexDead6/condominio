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
import com.rex.condominio.fragments.servicios.PagarServicioFragment;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.util.ArrayList;

public class ServiciosPorPagarAdapter extends RecyclerView.Adapter<ServiciosPorPagarAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServicioResponse> data;
    private OnClickResponse<ServicioResponse> onClickResponse;
    private float tasa;

    public ServiciosPorPagarAdapter(float tasa, ArrayList<ServicioResponse> data, OnClickResponse<ServicioResponse> onClickResponse) {
        this.data = data;
        this.onClickResponse = onClickResponse;
        this.tasa = tasa;
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
        if (data.get(position).getDivisa() == 0){
            holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer())+" Bs");
            holder.tv_total.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer() * data.get(position).getMesesPorPagar())+" Bs");
        } else {
            holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer())+" $");
            float totalDolar = data.get(position).getMontoSer() * data.get(position).getMesesPorPagar();
            holder.tv_total.setText(SupportPreferences.formatCurrency(totalDolar)+" $ = "+SupportPreferences.formatCurrency(totalDolar * tasa) + " Bs");
        }
        holder.tv_meses.setText(data.get(position).getIsMensualSer() == 0 ? "Pago unico" : data.get(position).getMesesPorPagar()+"");
        holder.btn_pagar.setOnClickListener(V-> onClickResponse.onClick(data.get(position)));
        holder.btn_pagar.setVisibility(SupportPreferences.getInstance(context).getPreference(SupportPreferences.JEFE_FAMILIA_PREFERENCE).equals("true") ? View.VISIBLE : View.GONE);
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
