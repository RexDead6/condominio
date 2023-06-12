package com.rex.condominio.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.request.ProductoCompraRequest;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

public class ProductosVentaAdapter extends RecyclerView.Adapter<ProductosVentaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductoResponse> data;

    public ProductosVentaAdapter(Context context, ArrayList<ProductoResponse> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ProductosVentaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_venta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosVentaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .load(SupportPreferences.BASE_URL_ASSETS+data.get(position).getImgPro())
                .into(holder.imgProducto);
        holder.tv_nombre.setText(data.get(position).getNomPro());
        holder.tv_costo.setText(SupportPreferences.formatCurrency(data.get(position).getCostoPro()));
        holder.et_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (holder.et_cantidad.getText().toString().equals("")){
                    holder.tv_total.setText("0,00 Bs");
                    return;
                }
                float total = data.get(position).getCostoPro() * Integer.parseInt(holder.et_cantidad.getText().toString());
                holder.tv_total.setText(SupportPreferences.formatCurrency(total));
                data.get(position).cantidad = Integer.parseInt(holder.et_cantidad.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public ArrayList<ProductoCompraRequest> getProductos(){
        ArrayList<ProductoCompraRequest> productos = new ArrayList<>();
        for (ProductoResponse producto : data){
            if (producto.cantidad > 0){
                productos.add(new ProductoCompraRequest(producto, producto.cantidad));
            }
        }
        return productos;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducto;
        private TextView tv_costo, tv_nombre;
        private EditText et_cantidad;
        private TextView tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_costo = itemView.findViewById(R.id.tv_costo);
            et_cantidad = itemView.findViewById(R.id.et_cantidad);
            tv_total = itemView.findViewById(R.id.tv_total);
        }
    }
}
