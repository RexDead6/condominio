package com.rex.condominio.retrofit;

import android.app.AlertDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onFinish();
        if (response.isSuccessful()){
            doCallBackResponse(response.body());
        }else{
            ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

            new AlertDialog.Builder(returnContext())
                    .setMessage(errorResponse.getMessage())
                    .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                    .create().show();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    public abstract Context returnContext();

    public abstract void onFinish();

    public abstract void doCallBackResponse(T response);
}
