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

import com.rex.condominio.R;
import com.rex.condominio.dialogs.BancosDialog;
import com.rex.condominio.dialogs.CrearPagoMovilDialog;
import com.rex.condominio.retrofit.response.BancosResponse;

import java.util.ArrayList;

public class BancosAdapter extends RecyclerView.Adapter<BancosAdapter.ViewHolder> implements Filterable {

    private ArrayList<BancosResponse> data;
    private ArrayList<BancosResponse> dataFiltered;
    private BancosDialog parent;

    public BancosAdapter(ArrayList<BancosResponse> data, BancosDialog parent) {
        this.data = data;
        this.dataFiltered = data;
        this.parent = parent;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence.length() == 0 || charSequence == null){
                    filterResults.count = dataFiltered.size();
                    filterResults.values = dataFiltered;
                } else {
                    String textInput = charSequence.toString().toLowerCase();
                    ArrayList<BancosResponse> newList = new ArrayList<>();
                    for (BancosResponse banco : dataFiltered){
                        if (banco.getCodBan().toLowerCase().contains(textInput) || banco.getNomBan().toLowerCase().contains(textInput)){
                            newList.add(banco);
                        }
                    }
                    filterResults.count = newList.size();
                    filterResults.values = newList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<BancosResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banco, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_codigo.setText(data.get(position).getCodBan());
        holder.tv_nombre.setText(data.get(position).getNomBan());
        holder.card_banco.setOnClickListener(V -> {
            parent.parent.obtenerBanco(data.get(position));
            parent.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_codigo, tv_nombre;
        private CardView card_banco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_codigo = itemView.findViewById(R.id.tv_codigo);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            card_banco = itemView.findViewById(R.id.card_banco);
        }
    }
}
