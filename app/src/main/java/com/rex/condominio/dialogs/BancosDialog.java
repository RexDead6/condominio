package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.BancosAdapter;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.BancosResponse;
import com.rex.condominio.retrofit.response.ResponseClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BancosDialog extends Dialog {

    public CrearPagoMovilDialog parent;
    private RecyclerView recycler_bancos;
    private BancosAdapter bancosAdapter;
    private ImageView btn_back;
    private EditText et_buscar;
    private LottieAnimationView animationView;

    public BancosDialog(@NonNull Context context, CrearPagoMovilDialog parent) {
        super(context);
        this.parent = parent;
        createDialog();
    }

    private void createDialog(){
        setContentView(R.layout.modal_select_items);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setWindowAnimations(R.style.dialogTranslationRight);

        recycler_bancos = findViewById(R.id.recycler_bancos);
        et_buscar = findViewById(R.id.et_buscar);
        btn_back = findViewById(R.id.btn_back);
        animationView = findViewById(R.id.animationView);

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bancosAdapter == null) return;
                bancosAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_back.setOnClickListener(V -> {
            dismiss();
        });

        Call<ResponseClient<ArrayList<BancosResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getBancos();
        call.enqueue(new Callback<ResponseClient<ArrayList<BancosResponse>>>() {
            @Override
            public void onResponse(Call<ResponseClient<ArrayList<BancosResponse>>> call, Response<ResponseClient<ArrayList<BancosResponse>>> response) {
                animationView.setVisibility(View.GONE);
                recycler_bancos.setVisibility(View.VISIBLE);
                if (response.code() == 200){
                    bancosAdapter = new BancosAdapter(response.body().getData(), BancosDialog.this);
                    recycler_bancos.setAdapter(bancosAdapter);
                    recycler_bancos.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<ResponseClient<ArrayList<BancosResponse>>> call, Throwable t) {
                Log.e("bancos", t.toString());
            }
        });
    }
}
