package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class CompraDetalleAdapter extends RecyclerView.Adapter<CompraDetalleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductoCompraRequest> data;

    public CompraDetalleAdapter(Context context, ArrayList<ProductoCompraRequest> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CompraDetalleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraDetalleAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(SupportPreferences.BASE_URL_ASSETS+data.get(position).getProducto().getImgPro())
                .into(holder.imgProducto);
        holder.tv_costo.setText(SupportPreferences.formatCurrency(data.get(position).getCosto()));
        holder.tv_nombre.setText(data.get(position).getProducto().getNomPro());
        holder.tv_cantidad.setText(data.get(position).getCantidad()+"");
        holder.tv_total.setText(SupportPreferences.formatCurrency(data.get(position).getCosto() * data.get(position).getCantidad()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProducto;
        private TextView tv_nombre, tv_costo, tv_cantidad, tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducto = itemView.findViewById(R.id.imgProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_costo = itemView.findViewById(R.id.tv_costo);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_total = itemView.findViewById(R.id.tv_total);
        }
    }
}
