package com.rex.condominio.fragments.tienda;

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
import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.activities.PDFActivity;
import com.rex.condominio.adapters.CompraAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.CompraResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CompraFragment extends Fragment {

    private LottieAnimationView animationView;
    private RecyclerView recycler_compra;
    private View view_not_found;
    private MaterialButton btn_crear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compra, container, false);

        animationView = v.findViewById(R.id.animationView);
        recycler_compra = v.findViewById(R.id.recycler_compra);
        view_not_found = v.findViewById(R.id.view_not_found);
        btn_crear = v.findViewById(R.id.btn_crear);

        btn_crear.setOnClickListener(V -> {
            SupportPreferences.loadFrament(new CrearCompraFragment(), getActivity().getSupportFragmentManager().beginTransaction(), true, R.id.container_productos);
        });

        call();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        view_not_found.setVisibility(View.GONE);
        recycler_compra.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        call();
    }

    private void call(){
        Call<ResponseClient<ArrayList<CompraResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getCompra(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<CompraResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<CompraResponse>> response) {
                recycler_compra.setVisibility(View.VISIBLE);
                recycler_compra.setAdapter(new CompraAdapter(getContext(), response.getData(), new OnClickResponse<CompraResponse>() {
                    @Override
                    public void onClick(CompraResponse object) {
                        Intent intent = new Intent(getActivity(), PDFActivity.class);
                        intent.putExtra("document", "compra_"+object.getIdCom()+".pdf");
                        startActivity(intent);
                    }
                }));
                recycler_compra.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                view_not_found.setVisibility(View.VISIBLE);
            }
        });
    }
}