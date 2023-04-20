package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class UsuariosProductosAdapter extends RecyclerView.Adapter<UsuariosProductosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UsuarioResponse> data;
    private OnClickResponse<UsuarioResponse> onClickResponse;

    public UsuariosProductosAdapter(Context context, ArrayList<UsuarioResponse> data, OnClickResponse<UsuarioResponse> onClickResponse) {
        this.context = context;
        this.data = data;
        this.onClickResponse = onClickResponse;
    }

    @NonNull
    @Override
    public UsuariosProductosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios_productos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosProductosAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(SupportPreferences.BASE_URL+data.get(position).getImgUsu())
                .into(holder.imageUser);
        holder.nombreUser.setText(data.get(position).getNomUsu() + " " +data.get(position).getApeUsu());

        Glide.with(context)
                .load(SupportPreferences.BASE_URL+data.get(position).getProductos().get(0).getImgPro())
                .into(holder.imageProducto1);
        holder.nombreProducto1.setText(data.get(position).getProductos().get(0).getNomPro());

        if (data.get(position).getProductos().size() > 1){
            Glide.with(context)
                    .load(SupportPreferences.BASE_URL+data.get(position).getProductos().get(1).getImgPro())
                    .into(holder.imageProducto2);
            holder.nombreProducto2.setText(data.get(position).getProductos().get(1).getNomPro());
            holder.layoutProducto2.setVisibility(View.VISIBLE);
        }

        holder.btn_ver_productos.setOnClickListener(V -> {
            onClickResponse.onClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutProducto1, layoutProducto2;
        private ImageView imageUser, imageProducto1, imageProducto2;
        private TextView nombreUser, nombreProducto1, nombreProducto2;
        private MaterialButton btn_ver_productos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutProducto1 = itemView.findViewById(R.id.layoutProducto1);
            layoutProducto2 = itemView.findViewById(R.id.layoutProducto2);
            imageUser = itemView.findViewById(R.id.imageUser);
            imageProducto1 = itemView.findViewById(R.id.imageProducto1);
            imageProducto2 = itemView.findViewById(R.id.imageProducto2);
            nombreUser = itemView.findViewById(R.id.nombreUser);
            nombreProducto1 = itemView.findViewById(R.id.nombreProducto1);
            nombreProducto2 = itemView.findViewById(R.id.nombreProducto2);
            btn_ver_productos = itemView.findViewById(R.id.btn_ver_productos);
        }
    }
}
