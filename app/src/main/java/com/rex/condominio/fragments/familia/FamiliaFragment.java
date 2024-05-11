package com.rex.condominio.fragments.familia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.FamiliaActivity;
import com.rex.condominio.adapters.FamiliaAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;

import net.glxn.qrgen.android.QRCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FamiliaFragment extends Fragment {

    private TextView tv_desc, tv_direccion;
    private ImageView image_qr;
    private RecyclerView recycler_familia;
    private LottieAnimationView animationView;
    private MaterialButton btn_qr;

    private Bitmap QR;
    private String direccion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_familia, container, false);

        tv_desc = v.findViewById(R.id.tv_desc);
        // tv_direccion = v.findViewById(R.id.tv_direccion);
        recycler_familia = v.findViewById(R.id.recycler_familia);
        animationView = v.findViewById(R.id.animationView);
        btn_qr = v.findViewById(R.id.btn_qr);

        btn_qr.setOnClickListener(V -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.modal_qr_familia);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ((ImageView) dialog.findViewById(R.id.imageQr)).setImageBitmap(QR);
            dialog.findViewById(R.id.btn_cerrar).setOnClickListener(V1 -> dialog.dismiss());
            dialog.show();
        });

        onCallFamilia();

        return v;
    }

    private void onCallFamilia() {
        Call<ResponseClient<FamiliaResponse>> call = RetrofitClient.getInstance().getRequestInterface().getFamilia(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<FamiliaResponse>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void doCallBackResponse(ResponseClient<FamiliaResponse> response) {
                QRCode qrCode = QRCode.from(response.getData().getHashFam())
                        .withSize(160, 160);

                QR = qrCode.bitmap();
                direccion = response.getData().getDireccion();
                tv_desc.setText(response.getData().getDescFam());

                animationView.setVisibility(View.GONE);
                recycler_familia.setVisibility(View.VISIBLE);

                recycler_familia.setAdapter(new FamiliaAdapter(getContext(), response.getData().getUsers(), new ResponseCallback<ResponseClient<Object>>() {
                    @Override
                    public Context returnContext() {
                        return getContext();
                    }

                    @Override
                    public void doCallBackResponse(ResponseClient<Object> response) {
                        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        onCallFamilia();
                    }
                }));
                recycler_familia.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}