package com.rex.condominio.retrofit;

import com.rex.condominio.retrofit.request.FamiliaRequest;
import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.request.PagarServicioRequest;
import com.rex.condominio.retrofit.request.PagoMovilRequest;
import com.rex.condominio.retrofit.request.RelacionFamiliarRequest;
import com.rex.condominio.retrofit.request.ServicioRequest;
import com.rex.condominio.retrofit.request.UsuarioRequest;
import com.rex.condominio.retrofit.response.AnuncioResponse;
import com.rex.condominio.retrofit.response.BancosResponse;
import com.rex.condominio.retrofit.response.FacturaByStatusResponse;
import com.rex.condominio.retrofit.response.FacturaResponse;
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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {

    @POST("login")
    Call<ResponseClient<TokenResponse>> login(@Body LoginRequest loginRequest);

    @POST("logout")
    Call<ResponseClient<Object>> logout(@Header("Authorization") String token);

    @POST("usuario")
    Call<ResponseClient<TokenResponse>> insertUsuario(@Body UsuarioRequest usuarioRequest);

    @GET("usuario/{id}")
    Call<ResponseClient<UsuarioResponse>> getUsuario(@Path("id") String id);

    @GET("usuarioInactivo")
    Call<ResponseClient<ArrayList<UsuarioResponse>>> getInactivos();

    @POST("familia")
    Call<ResponseClient<Object>> insertFamilia(
            @Header("Authorization") String token,
            @Body FamiliaRequest familiaRequest
    );

    @POST("relacionFamilia")
    Call<ResponseClient<TokenResponse>> relacionFamiliar(
            @Header("Authorization") String token,
            @Body RelacionFamiliarRequest relacionFamiliarRequest
    );

    @GET("familia")
    Call<ResponseClient<FamiliaResponse>> getFamilia(@Header("Authorization") String token);

    @GET("familias")
    Call<ResponseClient<ArrayList<FamiliaResponse>>> getAllFamilia(@Header("Authorization") String token);

    @Multipart
    @POST("anuncios")
    Call<ResponseClient<Object>> insertAnuncio(
            @Header("Authorization") String token,
            @Part MultipartBody.Part image,
            @Part("descAnu") RequestBody descAnu
    );

    @Multipart
    @POST("anuncios")
    Call<ResponseClient<Object>> insertAnuncio(
            @Header("Authorization") String token,
            @Part("descAnu") RequestBody descAnu
    );

    @GET("anuncios")
    Call<ResponseClient<ArrayList<AnuncioResponse>>> getAnuncios(@Header("Authorization") String token);

    @GET("bancos")
    Call<ResponseClient<ArrayList<BancosResponse>>> getBancos();

    @GET("pagoMovil")
    Call<ResponseClient<ArrayList<PagoMovilResponse>>> getPagoMovil(@Header("Authorization") String token);

    @POST("PagoMovil")
    Call<ResponseClient<Object>> insetPagoMovil(
            @Header("Authorization") String token,
            @Body PagoMovilRequest pagoMovilRequest
    );

    @POST("servicio")
    Call<ResponseClient<Object>> insertServicio(
            @Header("Authorization") String token,
            @Body ServicioRequest servicioRequest
    );

    @POST("factura")
    Call<ResponseClient<Object>> insertPagoServicio(
            @Header("Authorization") String token,
            @Body PagarServicioRequest pagarServicioRequest
    );

    @GET("factura/{status}")
    Call<ResponseClient<ArrayList<FacturaResponse>>> getFacturas(
            @Header("Authorization") String token,
            @Path("status") String status
    );

    @PATCH("FacturaAdmin/{idFac}/{status}")
    Call<ResponseClient<Object>> updateStatusFactura(
            @Header("Authorization") String token,
            @Path("idFac") String idFac,
            @Path("status") String status
    );

    @GET("FacturaAdmin/{idSer}/{status}")
    Call<ResponseClient<ArrayList<FacturaResponse>>> getFacturaAdmin(
            @Header("Authorization") String token,
            @Query("idSer") String idSer,
            @Query("status") String status
    );

    @GET("servicio")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getServicio(@Header("Authorization") String token);

    @GET("servicioAdmin")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getAdminServicio(@Header("Authorization") String token);

    @GET("notificaciones")
    Call<ResponseClient<PushMessageResponse>> getNotificaciones(@Header("Authorization") String token);
}
