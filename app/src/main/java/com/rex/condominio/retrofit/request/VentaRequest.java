package com.rex.condominio.retrofit.request;

import java.util.ArrayList;

public class VentaRequest {
    private int idVenUsu;
    private float monto;
    private ArrayList<ProductoCompraRequest> productosVenta;
    private ArrayList<PagoVentaRequest> pagos;

    public int getIdVenUsu() {
        return idVenUsu;
    }

    public void setIdVenUsu(int idVenUsu) {
        this.idVenUsu = idVenUsu;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public ArrayList<ProductoCompraRequest> getProductosVenta() {
        return productosVenta;
    }

    public void setProductosVenta(ArrayList<ProductoCompraRequest> productosVenta) {
        this.productosVenta = productosVenta;
    }

    public ArrayList<PagoVentaRequest> getPagos() {
        return pagos;
    }

    public void setPagos(ArrayList<PagoVentaRequest> pagos) {
        this.pagos = pagos;
    }
}
