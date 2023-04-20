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
import android.widget.Toast;

import com.rex.condominio.R;
import com.rex.condominio.activities.PDFActivity;
import com.rex.condominio.adapters.VentasListaAdapter;
import com.rex.condominio.dialogs.ProgressDialog;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.VentaResponse;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class VentasListasFragment extends Fragment {

    private String status;
    private String type;

    private RecyclerView recycler_ventas;
    private View not_found;

    public VentasListasFragment(String status, String type) {
        this.status = status;
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ventas_listas, container, false);

        recycler_ventas = v.findViewById(R.id.recycler_ventas);
        not_found = v.findViewById(R.id.not_found);

        call();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
    }

    private void call(){
        Call<ResponseClient<ArrayList<VentaResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getVentas(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                type,
                status
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<VentaResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<VentaResponse>> response) {
                recycler_ventas.setAdapter(new VentasListaAdapter(getContext(), response.getData(), type, status, new OnClickResponse<String[]>() {
                    @Override
                    public void onClick(String[] object) {
                        ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.show();

                        Call<ResponseClient<Object>> call1 = RetrofitClient.getInstance().getRequestInterface().updateStatusVenta(
                                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                object[0],
                                object[1]
                        );
                        call1.enqueue(new ResponseCallback<ResponseClient<Object>>() {
                            @Override
                            public Context returnContext() {
                                return getContext();
                            }

                            @Override
                            public void onFinish() {
                                dialog.dismiss();
                            }

                            @Override
                            public void doCallBackResponse(ResponseClient<Object> response) {
                                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                                call();
                            }
                        });
                    }
                }, new OnClickResponse<VentaResponse>() {
                    @Override
                    public void onClick(VentaResponse object) {
                        Intent intent = new Intent(getActivity(), PDFActivity.class);
                        intent.putExtra("document", "venta_"+object.getIdVen()+".pdf");
                        startActivity(intent);
                    }
                }));
                recycler_ventas.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_ventas.setVisibility(View.VISIBLE);
                not_found.setVisibility(View.GONE);
            }
            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                not_found.setVisibility(View.VISIBLE);
                recycler_ventas.setVisibility(View.GONE);
            }
        });
    }
}