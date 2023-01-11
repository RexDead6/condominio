package com.rex.condominio.retrofit.response;

public class BancosResponse {
    private int idBan;
    private String nomBan;
    private String codBan;

    public BancosResponse(int idBan, String nomBan, String codBan) {
        this.idBan = idBan;
        this.nomBan = nomBan;
        this.codBan = codBan;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public String getNomBan() {
        return nomBan;
    }

    public void setNomBan(String nomBan) {
        this.nomBan = nomBan;
    }

    public String getCodBan() {
        return codBan;
    }

    public void setCodBan(String codBan) {
        this.codBan = codBan;
    }
}
