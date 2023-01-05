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
import com.rex.condominio.retrofit.response.AnuncioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class AnunciosAdapter extends RecyclerView.Adapter<AnunciosAdapter.ViewHolder> {

    private ArrayList<AnuncioResponse> data;
    private Context context;

    public AnunciosAdapter(ArrayList<AnuncioResponse> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AnunciosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anuncio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnunciosAdapter.ViewHolder holder, int position) {
        holder.tv_nombre.setText(data.get(position).getUsuario().getNomUsu() + " " + data.get(position).getUsuario().getApeUsu());
        holder.tv_descripcion.setText(data.get(position).getDescAnu());
        holder.tv_fecha.setText(data.get(position).getFechaAnu());

        if (!data.get(position).getImage().equals("")) {
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(SupportPreferences.IMG_URL + data.get(position).getImage())
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nombre, tv_descripcion, tv_fecha;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            image = itemView.findViewById(R.id.image);
        }
    }
}
