package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.PagoMovilAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.OnClickResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

public class SelectPagoMovilDialog extends Dialog {

    private RecyclerView recycler_bancos;
    private LottieAnimationView animationView;
    private OnClickResponse<PagoMovilResponse> onClickResponse;
    private EditText et_buscar;
    private PagoMovilAdapter pagoMovilAdapter;
    private ImageView btn_back;

    public SelectPagoMovilDialog(@NonNull Context context, OnClickResponse<PagoMovilResponse> onClickResponse) {
        super(context);
        this.onClickResponse = onClickResponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_select_items);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setWindowAnimations(R.style.dialogTranslationRight);

        TextView tv_titulo = findViewById(R.id.tv_titulo);
        recycler_bancos = findViewById(R.id.recycler_bancos);
        animationView = findViewById(R.id.animationView);
        et_buscar = findViewById(R.id.et_buscar);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(v -> dismiss());

        tv_titulo.setText("Pago Movil");
        Call<ResponseClient<ArrayList<PagoMovilResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getPagoMovil(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<PagoMovilResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
                recycler_bancos.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<PagoMovilResponse>> response) {
                pagoMovilAdapter = new PagoMovilAdapter(response.getData(), true,new OnClickResponse<PagoMovilResponse>() {
                    @Override
                    public void onClick(PagoMovilResponse object) {
                        onClickResponse.onClick(object);
                        dismiss();
                    }
                });
                recycler_bancos.setAdapter(pagoMovilAdapter);
                recycler_bancos.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pagoMovilAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
