package com.rex.condominio.retrofit.response;

import java.util.ArrayList;

public class FacturaResponse {

    private int idFac;
    private FamiliaResponse Familia;
    private ServicioResponse Servicio;
    private float montoFac;
    private String fechapagFac;
    private int status;
    private int meses;
    private ArrayList<PagosServicioResponse> pagos;

    public FacturaResponse(int idFac, FamiliaResponse familia, ServicioResponse servicio, float montoFac, String fechapagFac, int status, int meses, ArrayList<PagosServicioResponse> pagos) {
        this.idFac = idFac;
        Familia = familia;
        Servicio = servicio;
        this.montoFac = montoFac;
        this.fechapagFac = fechapagFac;
        this.status = status;
        this.meses = meses;
        this.pagos = pagos;
    }

    public int getIdFac() {
        return idFac;
    }

    public void setIdFac(int idFac) {
        this.idFac = idFac;
    }

    public FamiliaResponse getFamilia() {
        return Familia;
    }

    public void setFamilia(FamiliaResponse familia) {
        Familia = familia;
    }

    public ServicioResponse getServicio() {
        return Servicio;
    }

    public void setServicio(ServicioResponse servicio) {
        Servicio = servicio;
    }

    public float getMontoFac() {
        return montoFac;
    }

    public void setMontoFac(float montoFac) {
        this.montoFac = montoFac;
    }

    public String getFechapagFac() {
        return fechapagFac;
    }

    public void setFechapagFac(String fechapagFac) {
        this.fechapagFac = fechapagFac;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public ArrayList<PagosServicioResponse> getPagos() {
        return pagos;
    }

    public void setPagos(ArrayList<PagosServicioResponse> pagos) {
        this.pagos = pagos;
    }
}
