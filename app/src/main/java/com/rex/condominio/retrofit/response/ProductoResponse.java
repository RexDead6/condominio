package com.rex.condominio.retrofit.response;

import java.io.Serializable;

public class ProductoResponse implements Serializable {
    private int idPro;
    private UsuarioResponse usuario;
    private String nomPro;
    private float costoPro;
    private int existPro;
    private String imgPro;
    private int status;

    public ProductoResponse(int idPro, UsuarioResponse usuario, String nomPro, float costoPro, int existPro, String imgPro, int status) {
        this.idPro = idPro;
        this.usuario = usuario;
        this.nomPro = nomPro;
        this.costoPro = costoPro;
        this.existPro = existPro;
        this.imgPro = imgPro;
        this.status = status;
    }

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public float getCostoPro() {
        return costoPro;
    }

    public void setCostoPro(float costoPro) {
        this.costoPro = costoPro;
    }

    public int getExistPro() {
        return existPro;
    }

    public void setExistPro(int existPro) {
        this.existPro = existPro;
    }

    public String getImgPro() {
        return imgPro;
    }

    public void setImgPro(String imgPro) {
        this.imgPro = imgPro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
