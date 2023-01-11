package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.adapters.FamiliaAdapter;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;

import net.glxn.qrgen.android.QRCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamiliaActivity extends AppCompatActivity {

    private TextView tv_desc, tv_direccion;
    private ImageView image_qr;
    private RecyclerView recycler_familia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familt);

        tv_desc = findViewById(R.id.tv_desc);
        tv_direccion = findViewById(R.id.tv_direccion);
        image_qr = findViewById(R.id.image_qr);
        recycler_familia = findViewById(R.id.recycler_familia);

        Call<ResponseClient<FamiliaResponse>> call = RetrofitClient.getInstance().getRequestInterface().getFamilia(
                SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE)
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

                    recycler_familia.setAdapter(new FamiliaAdapter(FamiliaActivity.this, response.body().getData().getUsers()));
                    recycler_familia.setLayoutManager(new LinearLayoutManager(FamiliaActivity.this));
                    return;
                }

                ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

                new AlertDialog.Builder(FamiliaActivity.this)
                        .setMessage(errorResponse.getMessage())
                        .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                        .create().show();
            }

            @Override
            public void onFailure(Call<ResponseClient<FamiliaResponse>> call, Throwable t) {
                Log.e("Familia", t.toString());
            }
        });
    }
}