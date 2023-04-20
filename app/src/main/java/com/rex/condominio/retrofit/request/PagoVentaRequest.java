package com.rex.condominio.retrofit.request;

public class PagoVentaRequest {
    private String tipoPag;
    private String refPag;
    private float monto;

    public PagoVentaRequest(String tipoPag, String refPag, float monto) {
        this.tipoPag = tipoPag;
        this.refPag = refPag;
        this.monto = monto;
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
