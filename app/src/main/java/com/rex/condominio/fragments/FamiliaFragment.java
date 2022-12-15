package com.rex.condominio.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.LoginResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_familia, container, false);

        tv_desc = v.findViewById(R.id.tv_desc);
        tv_direccion = v.findViewById(R.id.tv_direccion);
        image_qr = v.findViewById(R.id.image_qr);
        recycler_familia = v.findViewById(R.id.recycler_familia);

        Call<ResponseClient<FamiliaResponse>> call = RetrofitClient.getInstance().getRequestInterface().getFamilia(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.FAM_ID_PREFERENCE)
        );
        call.enqueue(new Callback<ResponseClient<FamiliaResponse>>() {
            @Override
            public void onResponse(Call<ResponseClient<FamiliaResponse>> call, Response<ResponseClient<FamiliaResponse>> response) {
                if (response.code() == 200) {
                    QRCode qrCode = QRCode.from(response.body().getData().getHashFam())
                            .withSize(160, 160);
                    image_qr.setImageBitmap(qrCode.bitmap());
                    tv_direccion.setText(response.body().getData().getDireccion());
                    tv_desc.setText(response.body().getData().getDescFam());

                    return;
                }

                ResponseClient<LoginResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

                new AlertDialog.Builder(getContext())
                        .setMessage(errorResponse.getMessage())
                        .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                        .create().show();
            }

            @Override
            public void onFailure(Call<ResponseClient<FamiliaResponse>> call, Throwable t) {
                Log.e("Familia", t.toString());
            }
        });
        return v;
    }
}