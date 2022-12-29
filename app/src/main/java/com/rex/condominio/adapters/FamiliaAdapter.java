package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UsuarioResponse> data;

    public FamiliaAdapter(Context context, ArrayList<UsuarioResponse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_familia, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_cedula.setText(data.get(position).getCedUsu());
        holder.tv_nombre.setText(data.get(position).getNomUsu()+" "+data.get(position).getApeUsu());
        holder.tv_rol.setText(data.get(position).getRol().getNomRol());

        Glide.with(context)
                .load(SupportPreferences.IMG_URL+data.get(position).getImgUsu())
                .into(holder.image_profile);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_profile;
        private TextView tv_cedula, tv_nombre, tv_rol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            tv_cedula = itemView.findViewById(R.id.tv_cedula);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_rol = itemView.findViewById(R.id.tv_rol);
        }
    }
}
