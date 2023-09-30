package com.rex.condominio.retrofit.request;

public class FamiliaRequest {
    private int idJefeUsu;
    private String descFam;
    private String direccion;
    private int idUrb;

    public FamiliaRequest(int idJefeUsu, String descFam, String direccion, int idUrb) {
        this.idJefeUsu = idJefeUsu;
        this.descFam = descFam;
        this.direccion = direccion;
        this.idUrb = idUrb;
    }

    public int getIdJefeUsu() {
        return idJefeUsu;
    }

    public void setIdJefeUsu(int idJefeUsu) {
        this.idJefeUsu = idJefeUsu;
    }

    public String getDescFam() {
        return descFam;
    }

    public void setDescFam(String descFam) {
        this.descFam = descFam;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdUrb() {
        return idUrb;
    }

    public void setIdUrb(int idUrb) {
        this.idUrb = idUrb;
    }
}
