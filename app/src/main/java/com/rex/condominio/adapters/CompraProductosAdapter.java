package com.rex.condominio.adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TextWatcherCurrency;

import java.util.ArrayList;

public class CompraProductosAdapter extends RecyclerView.Adapter<CompraProductosAdapter.ViewHolder> {

    private ArrayList<ProductoCompraRequest> data;
    private float porcentaje;

    public CompraProductosAdapter(ArrayList<ProductoCompraRequest> data, float porcentaje) {
        this.data = data;
        this.porcentaje = porcentaje;
    }

    @NonNull
    @Override
    public CompraProductosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraProductosAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                float costo = holder.et_costo.getText().toString().isEmpty() ? 0 : Float.parseFloat(holder.et_costo.getText().toString());
                int cantidad = holder.et_cantidad.getText().toString().isEmpty() ? 0 : Integer.parseInt(holder.et_cantidad.getText().toString());
                float total = cantidad * costo;
                float p_venta = costo + (costo * (porcentaje / 100));
                holder.et_total.setText(SupportPreferences.formatCurrency(total));
                holder.et_p_venta.setText(SupportPreferences.formatCurrency(p_venta));

                data.get(position).setCosto(costo);
                data.get(position).setCantidad(cantidad);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        //holder.et_costo.addTextChangedListener(new TextWatcherCurrency(holder.et_costo, 2));
        holder.et_cantidad.setText(data.get(position).getCantidad()+"");
        holder.tv_producto.setText(data.get(position).getProducto().getNomPro());
        holder.et_costo.addTextChangedListener(textWatcher);
        holder.et_cantidad.addTextChangedListener(textWatcher);
        holder.et_costo.setText(data.get(position).getCosto()+"");
        holder.btn_quitar.setOnClickListener(V -> {
            data.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<ProductoCompraRequest> getData() {
        return data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextInputEditText et_costo, et_cantidad, et_total, et_p_venta;
        private TextView tv_producto;
        private Button btn_quitar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_costo = itemView.findViewById(R.id.et_costo);
            et_cantidad = itemView.findViewById(R.id.et_cantidad);
            et_total = itemView.findViewById(R.id.et_total);
            et_p_venta = itemView.findViewById(R.id.et_p_venta);
            tv_producto = itemView.findViewById(R.id.tv_producto);
            btn_quitar = itemView.findViewById(R.id.btn_quitar);
        }
    }
}
