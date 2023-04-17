package com.rex.condominio.fragments.tienda;

import android.content.Context;
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
import com.rex.condominio.adapters.ProductosAdminAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProductosAdminFragment extends Fragment {

    private LottieAnimationView animationView;
    private RecyclerView recycler_productos;
    private View view_not_found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_productos_admin, container, false);

        animationView = v.findViewById(R.id.animationView);
        recycler_productos = v.findViewById(R.id.recycler_productos);
        view_not_found = v.findViewById(R.id.view_not_found);

        call();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        view_not_found.setVisibility(View.GONE);
        recycler_productos.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
    }

    private void call(){
        Call<ResponseClient<ArrayList<ProductoResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getAdminProductos(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<ProductoResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_not_found.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<ProductoResponse>> response) {
                recycler_productos.setAdapter(new ProductosAdminAdapter(getContext(), response.getData()));
                recycler_productos.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_productos.setVisibility(View.VISIBLE);
            }
        });
    }
}