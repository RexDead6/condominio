package com.rex.condominio.retrofit.response;

public class PagoMovilResponse {
    private int idPvm;
    private BancosResponse Banco;
    private int status;
    private String telPmv;
    private String cedPmv;

    public PagoMovilResponse(int idPvm, BancosResponse banco, int status, String telPmv, String cedPmv) {
        this.idPvm = idPvm;
        Banco = banco;
        this.status = status;
        this.telPmv = telPmv;
        this.cedPmv = cedPmv;
    }

    public int getIdPvm() {
        return idPvm;
    }

    public void setIdPvm(int idPvm) {
        this.idPvm = idPvm;
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
}
