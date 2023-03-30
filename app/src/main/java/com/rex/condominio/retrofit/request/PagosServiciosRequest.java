package com.rex.condominio.retrofit.request;

public class PagosServiciosRequest {
    private String tipoPag;
    private String refPag;
    private float montoPag;

    public PagosServiciosRequest(String tipoPag, String refPag, float montoPag) {
        this.tipoPag = tipoPag;
        this.refPag = refPag;
        this.montoPag = montoPag;
    }

    public String getTipoPag() {
        return tipoPag;
    }

    public void setTipoPag(String tipoPag) {
        this.tipoPag = tipoPag;
    }

    public String getRefPag() {
        return refPag;
    }

    public void setRefPag(String refPag) {
        this.refPag = refPag;
    }

    public float getMontoPag() {
        return montoPag;
    }

    public void setMontoPag(float montoPag) {
        this.montoPag = montoPag;
    }
}
