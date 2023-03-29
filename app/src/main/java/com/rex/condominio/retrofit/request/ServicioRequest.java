package com.rex.condominio.retrofit.request;

public class ServicioRequest {
    private int idPmv;
    private String descSer;
    private int isMensualSer;
    private double montoSer;

    public ServicioRequest(int idPmv, String descSer, int isMensualSer, double montoSer) {
        this.idPmv = idPmv;
        this.descSer = descSer;
        this.isMensualSer = isMensualSer;
        this.montoSer = montoSer;
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
}
