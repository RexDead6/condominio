package com.rex.condominio.retrofit.response;

public class PagoMovilResponse {
    private int idPmv;
    private BancosResponse Banco;
    private int status;
    private String telPmv;
    private String cedPmv;
    private int venta;

    public PagoMovilResponse(int idPmv, BancosResponse banco, int status, String telPmv, String cedPmv, int venta) {
        this.idPmv = idPmv;
        Banco = banco;
        this.status = status;
        this.telPmv = telPmv;
        this.cedPmv = cedPmv;
        this.venta = venta;
    }

    public int getIdPmv() {
        return idPmv;
    }

    public void setIdPmv(int idPmv) {
        this.idPmv = idPmv;
    }

    public BancosResponse getBanco() {
        return Banco;
    }

    public void setBanco(BancosResponse banco) {
        Banco = banco;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelPmv() {
        return telPmv;
    }

    public void setTelPmv(String telPmv) {
        this.telPmv = telPmv;
    }

    public String getCedPmv() {
        return cedPmv;
    }

    public void setCedPmv(String cedPmv) {
        this.cedPmv = cedPmv;
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }
}
