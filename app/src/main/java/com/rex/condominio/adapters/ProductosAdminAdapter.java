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
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class ProductosAdminAdapter extends RecyclerView.Adapter<ProductosAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductoResponse> data;
    private OnClickResponse<ProductoResponse> onClickResponse;

    public ProductosAdminAdapter(Context context, ArrayList<ProductoResponse> data) {
        this.context = context;
        this.data = data;
    }

    public ProductosAdminAdapter(Context context, ArrayList<ProductoResponse> data, OnClickResponse<ProductoResponse> onClickResponse) {
        this.context = context;
        this.data = data;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public ProductosAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdminAdapter.ViewHolder holder, int position) {
        holder.tv_nombre.setText(data.get(position).getNomPro());
        holder.tv_costo.setText(SupportPreferences.formatCurrency(data.get(position).getCostoPro()));
        holder.tv_existencia.setText(data.get(position).getExistPro()+"");
        Glide.with(context)
                .load(SupportPreferences.BASE_URL+data.get(position).getImgPro())
                .into(holder.imgProducto);
        if (onClickResponse != null) holder.card.setOnClickListener(V -> onClickResponse.onClick(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducto;
        private TextView tv_nombre, tv_costo, tv_existencia;
        private CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducto = itemView.findViewById(R.id.imgProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_costo = itemView.findViewById(R.id.tv_costo);
            tv_existencia = itemView.findViewById(R.id.tv_existencia);
            card = itemView.findViewById(R.id.card);
        }
    }
}
