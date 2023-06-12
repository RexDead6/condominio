package com.rex.condominio.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class UsuariosInactivosAdapter extends RecyclerView.Adapter<UsuariosInactivosAdapter.ViewHolder> implements Filterable {

    private ArrayList<UsuarioResponse> data;
    private ArrayList<UsuarioResponse> dataFilter;
    private Context context;
    private OnClickResponse onClickResponse;
    private Dialog dialog;

    public UsuariosInactivosAdapter(ArrayList<UsuarioResponse> data, Context context, OnClickResponse onClickResponse, Dialog dialog) {
        this.data = data;
        this.dataFilter = data;
        this.context = context;
        this.onClickResponse = onClickResponse;
        this.dialog = dialog;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence.length() == 0 || charSequence.equals("")){
                    filterResults.values = dataFilter;
                    filterResults.count = dataFilter.size();
                } else {
                    ArrayList<UsuarioResponse> newList = new ArrayList<>();
                    String search = charSequence.toString();
                    for (UsuarioResponse usuario : dataFilter){
                        if (usuario.getCedUsu().contains(search)){
                            newList.add(usuario);
                        }
                    }

                    filterResults.values = newList;
                    filterResults.count = newList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<UsuarioResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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

        Glide.with(context)
                .load(SupportPreferences.BASE_URL_ASSETS+data.get(position).getImgUsu())
                .into(holder.image_profile);

        holder.cardView.setOnClickListener(V -> {
            dialog.dismiss();
            onClickResponse.onClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView image_profile;
        private TextView tv_cedula, tv_nombre, tv_rol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            image_profile = itemView.findViewById(R.id.image_profile);
            tv_cedula = itemView.findViewById(R.id.tv_cedula);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_rol = itemView.findViewById(R.id.tv_rol);
            tv_rol.setVisibility(View.INVISIBLE);
        }
    }
}
