package com.rex.condominio.retrofit.response;

public class ServicioResponse {
    private int idSer;
    private PagoMovilResponse PagoMovil;
    private String descSer;
    private int isMensualSer;
    private float montoSer;
    private int statusSer;
    private String fechaInicioServicio;
    private int mesesPorPagar;
    private int divisa;

    public ServicioResponse(int idSer, PagoMovilResponse pagoMovil, String descSer, int isMensualSer, float montoSer, int statusSer, String fechaInicioServicio, int mesesPorPagar, int divisa) {
        this.idSer = idSer;
        PagoMovil = pagoMovil;
        this.descSer = descSer;
        this.isMensualSer = isMensualSer;
        this.montoSer = montoSer;
        this.statusSer = statusSer;
        this.fechaInicioServicio = fechaInicioServicio;
        this.mesesPorPagar = mesesPorPagar;
        this.divisa = divisa;
    }

    public int getIdSer() {
        return idSer;
    }

    public void setIdSer(int idSer) {
        this.idSer = idSer;
    }

    public PagoMovilResponse getPagoMovil() {
        return PagoMovil;
    }

    public void setPagoMovil(PagoMovilResponse pagoMovil) {
        PagoMovil = pagoMovil;
    }

    public String getDescSer() {
        return descSer;
    }

    public void setDescSer(String descSer) {
        this.descSer = descSer;
    }

    public int getIsMensualSer() {
        return isMensualSer;
    }

    public void setIsMensualSer(int isMensualSer) {
        this.isMensualSer = isMensualSer;
    }

    public float getMontoSer() {
        return montoSer;
    }

    public void setMontoSer(float montoSer) {
        this.montoSer = montoSer;
    }

    public int getStatusSer() {
        return statusSer;
    }

    public void setStatusSer(int statusSer) {
        this.statusSer = statusSer;
    }

    public String getFechaInicioServicio() {
        return fechaInicioServicio;
    }

    public void setFechaInicioServicio(String fechaInicioServicio) {
        this.fechaInicioServicio = fechaInicioServicio;
    }

    public int getMesesPorPagar() {
        return mesesPorPagar;
    }

    public void setMesesPorPagar(int mesesPorPagar) {
        this.mesesPorPagar = mesesPorPagar;
    }

    public int getDivisa() {
        return divisa;
    }

    public void setDivisa(int divisa) {
        this.divisa = divisa;
    }
}
