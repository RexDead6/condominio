package com.rex.condominio.retrofit;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.rex.condominio.activities.LoginActivity;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ResponseCallback<T> extends Callback<T> {

    @Override
    default void onResponse(Call<T> call, Response<T> response) {
        onFinish();
        if (response.isSuccessful()){
            doCallBackResponse(response.body());
        }else{
            ResponseClient<Object> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);
            doCallBackErrorResponse(errorResponse);
        }
    }

    @Override
    default void onFailure(Call<T> call, Throwable t) {
        Log.e("Error de petición", t.toString());
    }

    Context returnContext();

    default void onFinish(){}

    void doCallBackResponse(T response);

    default void doCallBackErrorResponse(ResponseClient<Object> response){
        new AlertDialog.Builder(returnContext())
                .setMessage(response.getMessage())
                .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                .create().show();
    }
}
