package com.rex.condominio.retrofit.response;

import com.rex.condominio.retrofit.request.PagoVentaRequest;

import java.util.ArrayList;

public class VentaResponse {
    private int idVen;
    private UsuarioResponse UsuarioVenta;
    private UsuarioResponse UsuarioCliente;
    private ArrayList<ProductoResponse> productos;
    private String fechaVen;
    private float montoVen;
    private int status;
    private ArrayList<PagoVentaRequest> pagos;

    public VentaResponse(int idVen, UsuarioResponse usuarioVenta, UsuarioResponse usuarioCliente, ArrayList<ProductoResponse> productos, String fechaVen, float montoVen, int status, ArrayList<PagoVentaRequest> pagos) {
        this.idVen = idVen;
        UsuarioVenta = usuarioVenta;
        UsuarioCliente = usuarioCliente;
        this.productos = productos;
        this.fechaVen = fechaVen;
        this.montoVen = montoVen;
        this.status = status;
        this.pagos = pagos;
    }

    public int getIdVen() {
        return idVen;
    }

    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public UsuarioResponse getUsuarioVenta() {
        return UsuarioVenta;
    }

    public void setUsuarioVenta(UsuarioResponse usuarioVenta) {
        UsuarioVenta = usuarioVenta;
    }

    public UsuarioResponse getUsuarioCliente() {
        return UsuarioCliente;
    }

    public void setUsuarioCliente(UsuarioResponse usuarioCliente) {
        UsuarioCliente = usuarioCliente;
    }

    public ArrayList<ProductoResponse> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoResponse> productos) {
        this.productos = productos;
    }

    public String getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(String fechaVen) {
        this.fechaVen = fechaVen;
    }

    public float getMontoVen() {
        return montoVen;
    }

    public void setMontoVen(float montoVen) {
        this.montoVen = montoVen;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<PagoVentaRequest> getPagos() {
        return pagos;
    }

    public void setPagos(ArrayList<PagoVentaRequest> pagos) {
        this.pagos = pagos;
    }
}
