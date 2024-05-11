package com.rex.condominio.retrofit.request;

import java.util.ArrayList;

public class PagarServicioRequest {
    private int idSer;
    private float montoFac;
    private ArrayList<PagosServiciosRequest> pagos;
    private int meses;

    public PagarServicioRequest(int idSer, float montoFac, ArrayList<PagosServiciosRequest> pagos, int meses) {
        this.idSer = idSer;
        this.montoFac = montoFac;
        this.pagos = pagos;
        this.meses = meses;
    }

    public int getIdSer() {
        return idSer;
    }

    public void setIdSer(int idSer) {
        this.idSer = idSer;
    }

    public float getMontoFac() {
        return montoFac;
    }

    public void setMontoFac(float montoFac) {
        this.montoFac = montoFac;
    }

    public ArrayList<PagosServiciosRequest> getPagos() {
        return pagos;
    }

    public void setPagos(ArrayList<PagosServiciosRequest> pagos) {
        this.pagos = pagos;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }
}
