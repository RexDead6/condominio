package com.rex.condominio.retrofit.request;

import java.util.ArrayList;

public class CompraRequest {

    private int idProv;
    private float monto;
    private float porcentaje;
    private ArrayList<ProductoCompraRequest> productosCompra;

    public CompraRequest(int idProv, float monto, float porcentaje, ArrayList<ProductoCompraRequest> productosCompra) {
        this.idProv = idProv;
        this.monto = monto;
        this.porcentaje = porcentaje;
        this.productosCompra = productosCompra;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public ArrayList<ProductoCompraRequest> getProductosCompra() {
        return productosCompra;
    }

    public void setProductosCompra(ArrayList<ProductoCompraRequest> productosCompra) {
        this.productosCompra = productosCompra;
    }
}
