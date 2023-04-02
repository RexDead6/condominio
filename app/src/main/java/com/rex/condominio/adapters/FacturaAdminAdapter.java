package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.dialogs.ConfirmYesNoDialog;
import com.rex.condominio.retrofit.response.FacturaResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TipoFactura;

import java.util.ArrayList;

public class FacturaAdminAdapter extends RecyclerView.Adapter<FacturaAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FacturaResponse> data;
    private int tipoFactura;
    private OnClickResponse<FacturaResponse> onClickResponse;

    public FacturaAdminAdapter(Context context, ArrayList<FacturaResponse> data, int tipoFactura, OnClickResponse<FacturaResponse> onClickResponse) {
        this.context = context;
        this.data = data;
        this.tipoFactura =  tipoFactura;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public FacturaAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factura_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaAdminAdapter.ViewHolder holder, int position) {
        holder.tv_id.setText(data.get(position).getIdFac()+"");
        holder.tv_familia.setText(data.get(position).getFamilia().getDescFam());
        holder.tv_meses.setText(data.get(position).getMeses()+"");
        holder.tv_fecha.setText(SupportPreferences.formatDate(data.get(position).getFechapagFac()));
        holder.tv_ref.setText(data.get(position).getPagos().get(0).getRefPag());
        holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoFac()));
        holder.container_btn.setVisibility(tipoFactura == TipoFactura.PENDIENTE ? View.VISIBLE : View.GONE);
        holder.btn_confirmar.setOnClickListener(V -> changeStatus(1, position));
        holder.btn_cancelar.setOnClickListener(V -> changeStatus(0, position));
    }

    private void changeStatus(int status, int position){
        new ConfirmYesNoDialog(context)
                .setMessage("¿Esta seguro de "+(status == 0 ? "rechazar" : "confirmar")+" la Factura #"+data.get(position).getIdFac()+"?")
                .setPositiveButtom(D -> {
                    data.get(position).setStatus(status);
                    onClickResponse.onClick(data.get(position));
                    data.remove(position);
                    notifyDataSetChanged();
                    D.dismiss();
                }).show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_id, tv_familia, tv_meses, tv_fecha, tv_ref, tv_monto;
        private Button btn_confirmar, btn_cancelar;
        private ConstraintLayout container_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_familia = itemView.findViewById(R.id.tv_familia);
            tv_meses = itemView.findViewById(R.id.tv_meses);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_ref = itemView.findViewById(R.id.tv_ref);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            container_btn = itemView.findViewById(R.id.container_btn);
            btn_confirmar = itemView.findViewById(R.id.btn_confirmar);
            btn_cancelar = itemView.findViewById(R.id.btn_cancelar);
        }
    }
}