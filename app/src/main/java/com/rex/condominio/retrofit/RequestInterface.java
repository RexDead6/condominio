package com.rex.condominio.retrofit;

import com.rex.condominio.retrofit.request.FamiliaRequest;
import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.request.PagoMovilRequest;
import com.rex.condominio.retrofit.request.RelacionFamiliarRequest;
import com.rex.condominio.retrofit.request.UsuarioRequest;
import com.rex.condominio.retrofit.response.AnuncioResponse;
import com.rex.condominio.retrofit.response.BancosResponse;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.PushMessageResponse;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RequestInterface {

    @POST("login.php")
    Call<ResponseClient<TokenResponse>> login(@Body LoginRequest loginRequest);

    @POST("logout.php")
    Call<ResponseClient<Object>> logout(@Header("Authorization") String token);

    @POST("usuario.php")
    Call<ResponseClient<TokenResponse>> insertUsuario(@Body UsuarioRequest usuarioRequest);

    @GET("usuario.php")
    Call<ResponseClient<UsuarioResponse>> getUsuario(@Query("id") String id);

    @GET("usuario_inactivo.php")
    Call<ResponseClient<ArrayList<UsuarioResponse>>> getInactivos();

    @POST("familia.php")
    Call<ResponseClient<Object>> insertFamilia(
            @Header("Authorization") String token,
            @Body FamiliaRequest familiaRequest
    );

    @POST("relacionFamilia.php")
    Call<ResponseClient<TokenResponse>> relacionFamiliar(
            @Header("Authorization") String token,
            @Body RelacionFamiliarRequest relacionFamiliarRequest
    );

    @GET("familia.php")
    Call<ResponseClient<FamiliaResponse>> getFamilia(@Header("Authorization") String token);

    @GET("familias.php")
    Call<ResponseClient<ArrayList<FamiliaResponse>>> getAllFamilia(@Header("Authorization") String token);

    @Multipart
    @POST("anuncios.php")
    Call<ResponseClient<Object>> insertAnuncio(
            @Header("Authorization") String token,
            @Part MultipartBody.Part image,
            @Part("descAnu") RequestBody descAnu
    );

    @Multipart
    @POST("anuncios.php")
    Call<ResponseClient<Object>> insertAnuncio(
            @Header("Authorization") String token,
            @Part("descAnu") RequestBody descAnu
    );

    @GET("anuncios.php")
    Call<ResponseClient<ArrayList<AnuncioResponse>>> getAnuncios(@Header("Authorization") String token);

    @GET("bancos.php")
    Call<ResponseClient<ArrayList<BancosResponse>>> getBancos();

    @GET("pagoMovil.php")
    Call<ResponseClient<ArrayList<PagoMovilResponse>>> getPagoMovil(@Header("Authorization") String token);

    @POST("PagoMovil.php")
    Call<ResponseClient<Object>> insetPagoMovil(
            @Header("Authorization") String token,
            @Body PagoMovilRequest pagoMovilRequest
    );

    @GET("servicio.php")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getServicio(@Header("Authorization") String token);

    @GET("servicioAdmin.php")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getAdminServicio(@Header("Authorization") String token);

    @GET("notificaciones.php")
    Call<ResponseClient<PushMessageResponse>> getNotificaciones(@Header("Authorization") String token);
}
