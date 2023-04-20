package com.rex.condominio.fragments.servicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.activities.PDFActivity;
import com.rex.condominio.adapters.FacturaAdapter;
import com.rex.condominio.adapters.FacturaAdminAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FacturaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PagerFacturaFragment extends Fragment {

    private int tipoFactua;

    private RecyclerView recycler_facturas;
    private LottieAnimationView animationView;
    private View container_not_found;

    public PagerFacturaFragment(int tipoFactua){
        this.tipoFactua = tipoFactua;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager_factura, container, false);

        recycler_facturas = v.findViewById(R.id.recycler_facturas);
        animationView = v.findViewById(R.id.animationView);
        container_not_found = v.findViewById(R.id.container_not_found);

        getFacturas();

        return v;
    }

    private void getFacturas(){
        Call<ResponseClient<ArrayList<FacturaResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getFacturas(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                tipoFactua+""
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<FacturaResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<FacturaResponse>> response) {
                recycler_facturas.setVisibility(View.VISIBLE);
                container_not_found.setVisibility(View.GONE);
                recycler_facturas.setAdapter(new FacturaAdapter(response.getData(), new OnClickResponse<FacturaResponse>() {
                    @Override
                    public void onClick(FacturaResponse object) {
                        Intent intent = new Intent(getActivity(), PDFActivity.class);
                        intent.putExtra("document", "servicio_"+object.getIdFac()+".pdf");
                        startActivity(intent);
                    }
                }));
                recycler_facturas.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                container_not_found.setVisibility(View.VISIBLE);
                recycler_facturas.setVisibility(View.GONE);
            }
        });
    }

    public void callStatus(FacturaResponse facturaResponse){
        Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().updateStatusFactura(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                facturaResponse.getIdFac()+"",
                facturaResponse.getStatus()+""
        );
        call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<Object> response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFacturas();
    }
}