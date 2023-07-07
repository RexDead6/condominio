package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.util.ArrayList;

import retrofit2.Call;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UsuarioResponse> data;
    private ResponseCallback<ResponseClient<Object>> responseCallback;

    public FamiliaAdapter(Context context, ArrayList<UsuarioResponse> data, ResponseCallback<ResponseClient<Object>> responseCallback) {
        this.context = context;
        this.data = data;
        this.responseCallback = responseCallback;
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
                .load(SupportPreferences.BASE_URL_ASSETS+data.get(position).getImgUsu())
                .into(holder.image_profile);

        if (SupportPreferences.getInstance(context).getPreference(SupportPreferences.JEFE_FAMILIA_PREFERENCE).equals("true")
        && new TokenSupport(context).getIdUsu() != data.get(position).getIdUsu() ){
            holder.btn_options.setVisibility(View.VISIBLE);
            holder.btn_options.setOnClickListener(V -> {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn_options);
                popupMenu.getMenuInflater().inflate(R.menu.options_usuarios_familia, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.option_eliminar:
                            Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().removerUsuarioFamilia(
                                    SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                    data.get(position).getIdUsu()+""
                            );
                            call.enqueue(responseCallback);
                            return true;
                        default:
                            return false;
                    }
                });
                popupMenu.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_profile;
        private TextView tv_cedula, tv_nombre, tv_rol;
        private ImageButton btn_options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            tv_cedula = itemView.findViewById(R.id.tv_cedula);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_rol = itemView.findViewById(R.id.tv_rol);
            btn_options = itemView.findViewById(R.id.btn_options);
        }
    }
}
