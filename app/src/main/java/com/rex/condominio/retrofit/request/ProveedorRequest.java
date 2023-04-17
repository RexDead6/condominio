package com.rex.condominio.retrofit.request;

public class ProveedorRequest {
    private String RIF;
    private String nomProv;

    public ProveedorRequest(String RIF, String nomProv) {
        this.RIF = RIF;
        this.nomProv = nomProv;
    }

    public String getRIF() {
        return RIF;
    }

    public void setRIF(String RIF) {
        this.RIF = RIF;
    }

    public String getNomProv() {
        return nomProv;
    }

    public void setNomProv(String nomProv) {
        this.nomProv = nomProv;
    }
}
