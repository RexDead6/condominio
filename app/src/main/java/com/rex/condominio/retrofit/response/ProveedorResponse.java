package com.rex.condominio.retrofit.response;

import java.io.Serializable;

public class ProveedorResponse implements Serializable {
    private int idProv;
    private String RIF;
    private String nomProv;

    public ProveedorResponse(int idProv, String RIF, String nomProv) {
        this.idProv = idProv;
        this.RIF = RIF;
        this.nomProv = nomProv;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
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
