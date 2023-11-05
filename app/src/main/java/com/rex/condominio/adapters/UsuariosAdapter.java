package com.rex.condominio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.dialogs.ConfirmYesNoDialog;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.EditJefeRequest;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.util.ArrayList;

import retrofit2.Call;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<UsuarioResponse> data;
    private ArrayList<UsuarioResponse> filteredData;
    private ResponseCallback<ResponseClient<Void>> callback;

    public UsuariosAdapter(Context context, ArrayList<UsuarioResponse> data, ResponseCallback<ResponseClient<Void>> callback) {
        this.context = context;
        this.data = data;
        this.filteredData = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public UsuariosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_familia, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosAdapter.ViewHolder holder, int position) {
        holder.tv_cedula.setText(data.get(position).getCedUsu());
        holder.tv_nombre.setText(data.get(position).getNomUsu()+" "+data.get(position).getApeUsu());
        holder.tv_rol.setText(data.get(position).getRol().getNomRol());

        if (data.get(position).isAdmin() && data.get(position).getRol().getNivelRol() != 1){
            holder.tv_rol.setText("Supervisor de condominio");
        }

        Glide.with(context)
                .load(SupportPreferences.BASE_URL_ASSETS+data.get(position).getImgUsu())
                .into(holder.image_profile);

        holder.btn_options.setVisibility(View.VISIBLE);
        holder.btn_options.setOnClickListener(V -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.btn_options);
            popupMenu.getMenuInflater().inflate(R.menu.options_usuario_admin, popupMenu.getMenu());

            if (data.get(position).isAdmin()) {
                popupMenu.getMenu().getItem(2).setVisible(false);
            }else{
                popupMenu.getMenu().getItem(3).setVisible(false);
            }

            if (data.get(position).getRol().getNivelRol() == 2){
                popupMenu.getMenu().getItem(1).setVisible(false);
            }else{
                popupMenu.getMenu().getItem(0).setVisible(false);
            }

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.option_admin_up:
                        new ConfirmYesNoDialog(context)
                                .setMessage("¿Esta seguro que desea promover a "+data.get(position).getNomUsu()+" "+data.get(position).getApeUsu()+" como administrador?")
                                .setPositiveButtom(dialog -> {
                                    dialog.dismiss();
                                    Call<ResponseClient<Void>> call = RetrofitClient.getInstance().getRequestInterface().updateRol(
                                            SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                            data.get(position).getIdUsu()+"",
                                            "1"
                                    );
                                    call.enqueue(callback);
                                }).show();
                        return true;
                    case R.id.option_admin_down:
                        new ConfirmYesNoDialog(context)
                                .setMessage("¿Esta seguro que desea remover a "+data.get(position).getNomUsu()+" "+data.get(position).getApeUsu()+" como administrador?")
                                .setPositiveButtom(dialog -> {
                                    dialog.dismiss();
                                    Call<ResponseClient<Void>> call = RetrofitClient.getInstance().getRequestInterface().updateRol(
                                            SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                            data.get(position).getIdUsu()+"",
                                            "2"
                                    );
                                    call.enqueue(callback);
                                }).show();
                        return true;
                    case R.id.option_urb_up:
                        new ConfirmYesNoDialog(context)
                                .setMessage("¿Esta seguro que desea promover a "+data.get(position).getNomUsu()+" "+data.get(position).getApeUsu()+" como supervisor de comunidad?")
                                .setPositiveButtom(dialog -> {
                                    dialog.dismiss();
                                    Call<ResponseClient<Void>> call = RetrofitClient.getInstance().getRequestInterface().updateAdminUrb(
                                            SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                            data.get(position).getIdUsu()+"",
                                            ""+ SupportPreferences.getInstance(context).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE)
                                    );
                                    call.enqueue(callback);
                                }).show();
                        return true;
                    case R.id.option_urb_down:
                        new ConfirmYesNoDialog(context)
                                .setMessage("¿Esta seguro que desea remover a "+data.get(position).getNomUsu()+" "+data.get(position).getApeUsu()+" como supervisor de comunidad?")
                                .setPositiveButtom(dialog -> {
                                    dialog.dismiss();
                                    Call<ResponseClient<Void>> call = RetrofitClient.getInstance().getRequestInterface().updateAdminUrb(
                                            SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                            data.get(position).getIdUsu()+"",
                                            ""+ SupportPreferences.getInstance(context).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE)
                                    );
                                    call.enqueue(callback);
                                }).show();
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults rs = new FilterResults();
                if (charSequence.equals("") || charSequence.length() == 0){
                    rs.values = filteredData;
                    rs.count = filteredData.size();
                }else{
                    ArrayList<UsuarioResponse> newList = new ArrayList<>();
                    for (UsuarioResponse usuario: filteredData){
                        if (usuario.getNomUsu().toLowerCase().contains(charSequence.toString().toLowerCase()) || usuario.getApeUsu().toLowerCase().contains(charSequence.toString().toLowerCase()) || usuario.getCedUsu().contains(charSequence.toString())){
                            newList.add(usuario);
                        }
                    }
                    rs.values = newList;
                    rs.count= newList.size();
                }
                return rs;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<UsuarioResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
