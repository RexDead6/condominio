package com.rex.condominio.retrofit;

import com.rex.condominio.retrofit.request.CompraRequest;
import com.rex.condominio.retrofit.request.EditJefeRequest;
import com.rex.condominio.retrofit.request.FamiliaRequest;
import com.rex.condominio.retrofit.request.LoginRequest;
import com.rex.condominio.retrofit.request.PagarServicioRequest;
import com.rex.condominio.retrofit.request.PagoMovilRequest;
import com.rex.condominio.retrofit.request.ProveedorRequest;
import com.rex.condominio.retrofit.request.RelacionFamiliarRequest;
import com.rex.condominio.retrofit.request.ServicioRequest;
import com.rex.condominio.retrofit.request.UsuarioRequest;
import com.rex.condominio.retrofit.request.VentaRequest;
import com.rex.condominio.retrofit.response.AjusteResponse;
import com.rex.condominio.retrofit.response.AnuncioResponse;
import com.rex.condominio.retrofit.response.BancosResponse;
import com.rex.condominio.retrofit.response.CompraResponse;
import com.rex.condominio.retrofit.response.ComunidadResponse;
import com.rex.condominio.retrofit.response.FacturaByStatusResponse;
import com.rex.condominio.retrofit.response.FacturaResponse;
import com.rex.condominio.retrofit.response.FamiliaResponse;
import com.rex.condominio.retrofit.response.PagoMovilResponse;
import com.rex.condominio.retrofit.response.ProductoResponse;
import com.rex.condominio.retrofit.response.ProveedorResponse;
import com.rex.condominio.retrofit.response.PushMessageResponse;
import com.rex.condominio.retrofit.response.ServicioResponse;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.UsuarioResponse;
import com.rex.condominio.retrofit.response.VentaResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("usuario/{idUsu}")
    Call<ResponseClient<UsuarioResponse>> getUsuById(
            @Path("idUsu") String idUsu
    );

    @POST("usuario")
    Call<ResponseClient<TokenResponse>> insertUsuario(@Body UsuarioRequest usuarioRequest);

    @GET("usuario/{id}")
    Call<ResponseClient<UsuarioResponse>> getUsuario(@Path("id") String id);

    @GET("usuarioInactivo")
    Call<ResponseClient<ArrayList<UsuarioResponse>>> getInactivos();

    @GET("usuarioUrb/{idUrb}")
    Call<ResponseClient<ArrayList<UsuarioResponse>>> getUserByComunidad(
            @Path("idUrb") String idUrb
    );

    @Multipart
    @POST("usuario/image")
    Call<ResponseClient<Object>> updateImageUser(
            @Header("Authorization") String token,
            @Part List<MultipartBody.Part> formData
    );

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

    @DELETE("removeUserFam/{id}")
    Call<ResponseClient<Object>> removerUsuarioFamilia(
            @Header("Authorization") String token,
            @Path("id") String idUsu
    );

    @PATCH("editJefeFam")
    Call<ResponseClient<Object>> editJefeFam(
            @Header("Authorization") String token,
            @Body EditJefeRequest editJefeRequest
    );

    @GET("familia")
    Call<ResponseClient<FamiliaResponse>> getFamilia(@Header("Authorization") String token);

    @GET("familias")
    Call<ResponseClient<ArrayList<FamiliaResponse>>> getAllFamilia(@Header("Authorization") String token);

    @GET("familiasComunidad/{idUrb}")
    Call<ResponseClient<ArrayList<FamiliaResponse>>> getFamByComunidad(@Path("idUrb") String idUrb);

    @Multipart
    @POST("anuncios")
    Call<ResponseClient<Object>> insertAnuncio(
            @Header("Authorization") String token,
            @Part List<MultipartBody.Part> formData
    );

    @GET("anuncios")
    Call<ResponseClient<ArrayList<AnuncioResponse>>> getAnuncios(@Header("Authorization") String token);

    @GET("bancos")
    Call<ResponseClient<ArrayList<BancosResponse>>> getBancos();

    @GET("pagoMovil")
    Call<ResponseClient<ArrayList<PagoMovilResponse>>> getPagoMovil(@Header("Authorization") String token);

    @POST("pagoMovil")
    Call<ResponseClient<Object>> insetPagoMovil(
            @Header("Authorization") String token,
            @Body PagoMovilRequest pagoMovilRequest
    );

    @GET("pagoMovil/{idUsu}")
    Call<ResponseClient<PagoMovilResponse>> getCurrentPvm(
            @Path("idUsu") String idUsu
    );

    @PATCH("pagoMovilVenta/{idPvm}")
    Call<ResponseClient<Object>> updatePagomVenta(
            @Header("Authorization") String token,
            @Path("idPvm") String idPvm
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

    @PATCH("facturaAdmin/{idFac}/{status}")
    Call<ResponseClient<Object>> updateStatusFactura(
            @Header("Authorization") String token,
            @Path("idFac") String idFac,
            @Path("status") String status
    );

    @GET("facturaAdmin/{idSer}/{status}")
    Call<ResponseClient<ArrayList<FacturaResponse>>> getFacturaAdmin(
            @Header("Authorization") String token,
            @Path("idSer") String idSer,
            @Path("status") String status
    );

    @GET("servicio/{idUrb}")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getServicio(
            @Header("Authorization") String token,
            @Path("idUrb") String idUrb
    );

    @GET("servicioAdmin/{idUrb}")
    Call<ResponseClient<ArrayList<ServicioResponse>>> getAdminServicio(
            @Header("Authorization") String token,
            @Path("idUrb") String idUrb
    );

    @Multipart
    @POST("producto")
    Call<ResponseClient<Object>> insertProducto(
            @Header("Authorization") String token,
            @Part List<MultipartBody.Part> formData
    );

    @GET("productoAdmin")
    Call<ResponseClient<ArrayList<ProductoResponse>>> getAdminProductos(
            @Header("Authorization") String token
    );

    @POST("proveedor")
    Call<ResponseClient<Object>> insertProveedor(
            @Header("Authorization") String token,
            @Body ProveedorRequest proveedorRequest
    );

    @GET("proveedor")
    Call<ResponseClient<ArrayList<ProveedorResponse>>> getProveedor(
            @Header("Authorization") String token
    );

    @POST("compra")
    Call<ResponseClient<Object>> insertCompra(
            @Header("Authorization") String token,
            @Body CompraRequest compraRequest
    );

    @GET("compra")
    Call<ResponseClient<ArrayList<CompraResponse>>> getCompra(
            @Header("Authorization") String token
    );

    @GET("ventaUsuarios")
    Call<ResponseClient<ArrayList<UsuarioResponse>>> getVentaUsuarios(
            @Header("Authorization") String token
    );

    @POST("venta")
    Call<ResponseClient<Object>> insertVenta(
            @Header("Authorization") String token,
            @Body VentaRequest ventaRequest
    );

    @GET("venta/{type}/{status}")
    Call<ResponseClient<ArrayList<VentaResponse>>> getVentas(
            @Header("Authorization") String token,
            @Path("type") String type,
            @Path("status") String status
    );

    @PATCH("venta/{idVen}/{status}")
    Call<ResponseClient<Object>> updateStatusVenta(
            @Header("Authorization") String token,
            @Path("idVen") String idVen,
            @Path("status") String status
    );

    @GET("notificaciones")
    Call<ResponseClient<PushMessageResponse>> getNotificaciones(@Header("Authorization") String token);

    @GET("ajuste/{ajuste}")
    Call<ResponseClient<AjusteResponse>> getAjuste(
            @Path("ajuste") String ajuste
    );

    @GET("comunidad")
    Call<ResponseClient<ArrayList<ComunidadResponse>>> getComunidadesAdmin();

    @GET("comunidad/usuario")
    Call<ResponseClient<ArrayList<ComunidadResponse>>> getComunidadesUsuario(@Header("Authorization") String token);

    @POST("comunidad")
    Call<ResponseClient<Object>> insertComunidad(
            @Header("Authorization") String token,
            @Body ComunidadResponse comunidad
    );
}
