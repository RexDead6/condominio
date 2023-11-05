package com.rex.condominio.fragments.admin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.rex.condominio.R;
import com.rex.condominio.adapters.UsuariosAdapter;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserAdminFragment extends Fragment {

    private RecyclerView recycler_usuarios;
    private View not_found;
    private LottieAnimationView animationView;
    private EditText et_buscar;

    private UsuariosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_admin, container, false);

        recycler_usuarios = v.findViewById(R.id.recycler_usuarios);
        not_found = v.findViewById(R.id.not_found);
        animationView = v.findViewById(R.id.animationView);
        et_buscar = v.findViewById(R.id.et_buscar);

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapter != null){
                    adapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        call_api();

        return v;
    }

    private void call_api() {
        not_found.setVisibility(View.GONE);
        recycler_usuarios.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        Call<ResponseClient<ArrayList<UsuarioResponse>>> call = RetrofitClient.getInstance().getRequestInterface().getUserByComunidad(
                ""+ SupportPreferences.getInstance(getContext()).getPreferenceInt(SupportPreferences.COMUNIDAD_ACTUAL_ADMIN_PREFERENCE)
        );
        call.enqueue(new ResponseCallback<ResponseClient<ArrayList<UsuarioResponse>>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void onFinish() {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void doCallBackResponse(ResponseClient<ArrayList<UsuarioResponse>> response) {
                adapter = new UsuariosAdapter(getContext(), response.getData(), new ResponseCallback<ResponseClient<Void>>() {
                    @Override
                    public Context returnContext() {
                        return getContext();
                    }

                    @Override
                    public void doCallBackResponse(ResponseClient<Void> response) {
                        call_api();
                    }
                });
                recycler_usuarios.setAdapter(adapter);
                recycler_usuarios.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_usuarios.setVisibility(View.VISIBLE);
            }

            @Override
            public void doCallBackErrorResponse(ResponseClient<Object> response) {
                not_found.setVisibility(View.VISIBLE);
            }
        });
    }
}