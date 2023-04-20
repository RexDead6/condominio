package com.rex.condominio.retrofit.request;

import com.rex.condominio.retrofit.response.ProductoResponse;

public class ProductoCompraRequest {
    private ProductoResponse producto;
    private int cantidad;
    private float costo;

    public ProductoCompraRequest(ProductoResponse producto, int cantidad, float costo) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public ProductoCompraRequest(ProductoResponse producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoCompraRequest(ProductoResponse producto){
        this.producto = producto;
        this.cantidad = 0;
        this.costo = 0;
    }

    public ProductoResponse getProducto() {
        return producto;
    }

    public void setProducto(ProductoResponse producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
