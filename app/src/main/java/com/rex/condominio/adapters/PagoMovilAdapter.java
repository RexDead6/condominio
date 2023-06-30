package com.rex.condominio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.utils.OnClickResponse;

import java.util.ArrayList;

public class PagoMovilAdapter extends RecyclerView.Adapter<PagoMovilAdapter.ViewHolder> implements Filterable {

    private ArrayList<PagoMovilResponse> data;
    private ArrayList<PagoMovilResponse> dataFilter;
    private OnClickResponse<PagoMovilResponse> onClickResponse;
    private boolean isService;

    public PagoMovilAdapter(ArrayList<PagoMovilResponse> data, boolean isService ,OnClickResponse<PagoMovilResponse> onClickResponse) {
        this.data = data;
        this.dataFilter = data;
        this.onClickResponse = onClickResponse;
        this.isService = isService;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults ft = new FilterResults();
                if (charSequence.length() == 0 || charSequence.equals("")){
                    ft.values = dataFilter;
                    ft.count = dataFilter.size();
                } else {
                    ArrayList<PagoMovilResponse> newList = new ArrayList<>();
                    String filter = charSequence.toString().toLowerCase();
                    for (PagoMovilResponse pagoMovil : dataFilter){
                        if (pagoMovil.getTelPmv().equals(filter) || pagoMovil.getCedPmv().equals(filter) || pagoMovil.getBanco().getNomBan().toLowerCase().equals(filter) || pagoMovil.getBanco().getCodBan().equals(filter)){
                            newList.add(pagoMovil);
                        }
                    }
                    ft.values = newList;
                    ft.count = newList.size();
                }
                return ft;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<PagoMovilResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
        holder.btn_ventas.setOnClickListener(V -> onClickResponse.onClick(data.get(position)));

        if (isService){
            holder.btn_ventas.setText("Seleccionar");
            holder.btn_ventas.setVisibility(View.VISIBLE);
        } else {
            if (data.get(position).getVenta() == 1){
                holder.tv_ventas.setVisibility(View.VISIBLE);
            } else {
                holder.btn_ventas.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_cedula, tv_telefono, tv_banco, tv_ventas;
        private MaterialButton btn_ventas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cedula = itemView.findViewById(R.id.tv_cedula);
            tv_telefono = itemView.findViewById(R.id.tv_telefono);
            tv_banco = itemView.findViewById(R.id.tv_banco);
            tv_ventas = itemView.findViewById(R.id.tv_ventas);
            btn_ventas = itemView.findViewById(R.id.btn_ventas);
        }
    }
}
