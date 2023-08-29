package com.rex.condominio.retrofit.request;

public class ServicioRequest {
    private int idPmv;
    private String descSer;
    private int isMensualSer;
    private double montoSer;
    private int divisa;
    private int idUrb;

    public ServicioRequest(int idPmv, String descSer, int isMensualSer, double montoSer, int divisa, int idUrb) {
        this.idPmv = idPmv;
        this.descSer = descSer;
        this.isMensualSer = isMensualSer;
        this.montoSer = montoSer;
        this.divisa = divisa;
        this.idUrb = idUrb;
    }

    public int getIdPmv() {
        return idPmv;
    }

    public void setIdPmv(int idPmv) {
        this.idPmv = idPmv;
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

    public double getMontoSer() {
        return montoSer;
    }

    public void setMontoSer(double montoSer) {
        this.montoSer = montoSer;
    }

    public int getDivisa() {
        return divisa;
    }

    public void setDivisa(int divisa) {
        this.divisa = divisa;
    }

    public int getIdUrb() {
        return idUrb;
    }

    public void setIdUrb(int idUrb) {
        this.idUrb = idUrb;
    }
}
