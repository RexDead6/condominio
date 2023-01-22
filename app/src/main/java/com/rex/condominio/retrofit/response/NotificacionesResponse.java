package com.rex.condominio.retrofit.response;

public class NotificacionesResponse {

    private int idNot;
    private String tituloNot;
    private String descNot;
    private String imgNot;
    private String tipoNot;

    public NotificacionesResponse(int idNot, String tituloNot, String descNot, String imgNot, String tipoNot) {
        this.idNot = idNot;
        this.tituloNot = tituloNot;
        this.descNot = descNot;
        this.imgNot = imgNot;
        this.tipoNot = tipoNot;
    }

    public int getIdNot() {
        return idNot;
    }

    public void setIdNot(int idNot) {
        this.idNot = idNot;
    }

    public String getTituloNot() {
        return tituloNot;
    }

    public void setTituloNot(String tituloNot) {
        this.tituloNot = tituloNot;
    }

    public String getDescNot() {
        return descNot;
    }

    public void setDescNot(String descNot) {
        this.descNot = descNot;
    }

    public String getImgNot() {
        return imgNot;
    }

    public void setImgNot(String imgNot) {
        this.imgNot = imgNot;
    }

    public String getTipoNot() {
        return tipoNot;
    }

    public void setTipoNot(String tipoNot) {
        this.tipoNot = tipoNot;
    }
}
