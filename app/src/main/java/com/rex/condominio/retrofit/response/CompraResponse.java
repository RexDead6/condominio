package com.rex.condominio.retrofit.response;

import com.rex.condominio.retrofit.request.ProductoCompraRequest;

import java.util.ArrayList;

public class CompraResponse {

    private int idCom;
    private ProveedorResponse proveedor;
    private float monto;
    private float porcentaje;
    ArrayList<ProductoCompraRequest> productos;

    public CompraResponse(int idCom, ProveedorResponse proveedor, float monto, float porcentaje, ArrayList<ProductoCompraRequest> productos) {
        this.idCom = idCom;
        this.proveedor = proveedor;
        this.monto = monto;
        this.porcentaje = porcentaje;
        this.productos = productos;
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public ProveedorResponse getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorResponse proveedor) {
        this.proveedor = proveedor;
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

    public ArrayList<ProductoCompraRequest> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoCompraRequest> productos) {
        this.productos = productos;
    }
}
