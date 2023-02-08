package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.UsuariosInactivosAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.OnClickResponse;

import java.util.ArrayList;

import retrofit2.Call;

public class UsuariosInactivosDialog extends Dialog {

    private LottieAnimationView animationView;
    private RecyclerView recycler_usuarios;
    private UsuariosInactivosAdapter usuariosInactivosAdapter;
    private EditText et_buscar;
    private ImageView btn_back;

    public UsuariosInactivosDialog(@NonNull Context context) {
        super(context);
    }

    public void onCreate(OnClickResponse onClickResponse){
        setContentView(R.layout.modal_usuarios_inactivos);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setWindowAnimations(R.style.dialogTranslationRight);

        et_buscar = findViewById(R.id.et_buscar);
        animationView = findViewById(R.id.animationView);
        recycler_usuarios = findViewById(R.id.recycler_usuarios);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(V -> dismiss());

        Call<ResponseClient<ArrayList<UsuarioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getInactivos();
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<UsuarioResponse>>>() {
            @Override
            public Context returnContext() {return getContext();}

            @Override
            public void onFinish() {}

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<UsuarioResponse>> response) {
                animationView.setVisibility(View.GONE);
                recycler_usuarios.setVisibility(View.VISIBLE);

                usuariosInactivosAdapter = new UsuariosInactivosAdapter(response.getData(), getContext(), onClickResponse, UsuariosInactivosDialog.this);
                recycler_usuarios.setAdapter(usuariosInactivosAdapter);
                recycler_usuarios.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usuariosInactivosAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
