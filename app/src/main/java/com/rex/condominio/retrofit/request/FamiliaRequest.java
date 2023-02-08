package com.rex.condominio.retrofit.request;

public class FamiliaRequest {
    private int idJefeUsu;
    private String descFam;
    private String direccion;

    public FamiliaRequest(int idJefeUsu, String descFam, String direccion) {
        this.idJefeUsu = idJefeUsu;
        this.descFam = descFam;
        this.direccion = direccion;
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
}
