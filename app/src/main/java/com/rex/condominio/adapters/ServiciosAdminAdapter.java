package com.rex.condominio.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rex.condominio.R;
import com.rex.condominio.activities.PDFActivity;
import com.rex.condominio.dialogs.PagoMovilDialog;
import com.rex.condominio.fragments.servicios.AdministrarServiciosFragment;
import com.rex.condominio.fragments.servicios.FacturasAdminFragment;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ReportResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class ServiciosAdminAdapter extends RecyclerView.Adapter<ServiciosAdminAdapter.ViewHolder> {

    private Fragment fragment;
    private ArrayList<ServicioResponse> data;
    private ArrayList<ServicioResponse> dataFiltered;
    private float tasa;

    public ServiciosAdminAdapter(Fragment fragment, ArrayList<ServicioResponse> data, float tasa) {
        this.fragment = fragment;
        this.data = data;
        this.dataFiltered = data;
        this.tasa = tasa;
    }

    @NonNull
    @Override
    public ServiciosAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosAdminAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(data.get(position).getDescSer());

        if (data.get(position).getDivisa() == 0)
            holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer())+" Bs");
        else
            holder.tv_monto.setText(SupportPreferences.formatCurrency(data.get(position).getMontoSer())+" $");
        holder.tv_estado.setText((data.get(position).getStatusSer() == 1) ? "Activo" : "Inactivo");
        holder.tv_tipo.setText((data.get(position).getIsMensualSer() == 1) ? "Mensual" : "Pago unico");
        holder.tv_fecha.setText(SupportPreferences.formatDate(data.get(position).getFechaInicioServicio()));

        holder.btn_administrar.setOnClickListener(v -> {
            SupportPreferences.loadFrament(new FacturasAdminFragment(data.get(position).getIdSer()), fragment.getActivity().getSupportFragmentManager().beginTransaction(), true, R.id.container_servicios);
        });

        holder.btn_pagado.setOnClickListener(V -> {
            Call<ResponseClient<ReportResponse>> call = RetrofitClient.getInstance().getRequestInterface().getPagados(data.get(position).getIdSer()+"");
            call.enqueue(new ResponseCallback<ResponseClient<ReportResponse>>() {
                @Override
                public Context returnContext() {
                    return fragment.getContext();
                }

                @Override
                public void doCallBackResponse(ResponseClient<ReportResponse> response) {
                    Intent intent = new Intent(fragment.getContext(), PDFActivity.class);
                    intent.putExtra("document", response.getData().getName());
                    fragment.getActivity().startActivity(intent);
                }
            });
        });

        holder.btn_no_pagado.setOnClickListener(V -> {
            Call<ResponseClient<ReportResponse>> call = RetrofitClient.getInstance().getRequestInterface().getNoPagados(data.get(position).getIdSer()+"");
            call.enqueue(new ResponseCallback<ResponseClient<ReportResponse>>() {
                @Override
                public Context returnContext() {
                    return fragment.getContext();
                }

                @Override
                public void doCallBackResponse(ResponseClient<ReportResponse> response) {
                    Intent intent = new Intent(fragment.getContext(), PDFActivity.class);
                    intent.putExtra("document", response.getData().getName());
                    fragment.getActivity().startActivity(intent);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_desc, tv_monto, tv_tipo, tv_estado, tv_fecha;
        private Button btn_editar, btn_administrar, btn_pagado, btn_no_pagado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            tv_tipo = itemView.findViewById(R.id.tv_tipo);
            tv_estado = itemView.findViewById(R.id.tv_estado);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            btn_administrar = itemView.findViewById(R.id.btn_administrar);
            btn_pagado = itemView.findViewById(R.id.btn_pagado);
            btn_no_pagado =  itemView.findViewById(R.id.btn_no_pagado);
        }
    }
}
