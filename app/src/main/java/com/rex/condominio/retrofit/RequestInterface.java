package com.rex.condominio.retrofit;

import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.response.LoginResponse;
import com.rex.condominio.retrofit.response.ResponseClient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("login.php")
    Call<ResponseClient<LoginResponse>> login(@Body LoginRequest loginRequest);
}
