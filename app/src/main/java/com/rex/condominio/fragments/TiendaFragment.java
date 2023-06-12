package com.rex.condominio.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.tienda.ProductosActivity;
import com.rex.condominio.activities.tienda.VentaActivity;
import com.rex.condominio.activities.tienda.VentasListasActivity;
import com.rex.condominio.adapters.UsuariosProductosAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TiendaFragment extends Fragment implements Serializable {

    private FloatingActionButton btn_productos, btn_ventas;
    private LottieAnimationView animationView;
    private View view_not_found;
    private RecyclerView recycler_usuarios_productos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tienda, container, false);

        btn_productos = v.findViewById(R.id.btn_productos);
        btn_ventas = v.findViewById(R.id.btn_ventas);
        recycler_usuarios_productos = v.findViewById(R.id.recycler_usuarios_productos);
        animationView = v.findViewById(R.id.animationView);
        view_not_found = v.findViewById(R.id.view_not_found);

        btn_productos.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), ProductosActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    btn_productos,
                    "producto"
            );
            startActivity(intent, options.toBundle());
        });

        btn_ventas.setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), VentasListasActivity.class);
            intent.putExtra("type", "cliente");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    btn_productos,
                    "ventas"
            );
            startActivity(intent, options.toBundle());
        });

        call();

        return v;
    }

    private void call(){
        Call<ResponseClient<ArrayList<UsuarioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getVentaUsuarios(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<UsuarioResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<UsuarioResponse>> response) {
                recycler_usuarios_productos.setAdapter(new UsuariosProductosAdapter(getContext(), response.getData(), new OnClickResponse<UsuarioResponse>() {
                    @Override
                    public void onClick(UsuarioResponse object) {
                        Intent intent = new Intent(getActivity(), VentaActivity.class);
                        intent.putExtra("vendedor", object);
                        startActivity(intent);
                    }
                }));
                recycler_usuarios_productos.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_usuarios_productos.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_not_found.setVisibility(View.VISIBLE);
            }
        });
    }
}