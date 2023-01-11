package com.rex.condominio.retrofit.request;

public class PagoMovilRequest {
    private int idBan;
    private String telPmv;
    private String cedPmv;

    public PagoMovilRequest(int idBan, String telPmv, String cedPvm) {
        this.idBan = idBan;
        this.telPmv = telPmv;
        this.cedPmv = cedPvm;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
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
