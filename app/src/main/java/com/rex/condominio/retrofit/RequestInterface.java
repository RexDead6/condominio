package com.rex.condominio.retrofit;

import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.request.RelacionFamiliarRequest;
import com.rex.condominio.retrofit.request.UsuarioRequest;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.retrofit.response.ResponseClient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {

    @POST("login.php")
    Call<ResponseClient<TokenResponse>> login(@Body LoginRequest loginRequest);

    @POST("usuario.php")
    Call<ResponseClient<TokenResponse>> insertUsuario(@Body UsuarioRequest usuarioRequest);

    @POST("relacionFamilia.php")
    Call<ResponseClient<TokenResponse>> relacionFamiliar(
            @Query("token") String token,
            @Body RelacionFamiliarRequest relacionFamiliarRequest
    );

    @GET("familia.php")
    Call<ResponseClient<FamiliaResponse>> getFamilia(@Query("token") String token);
}
