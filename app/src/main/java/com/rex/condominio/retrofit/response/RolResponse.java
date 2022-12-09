package com.rex.condominio.retrofit.response;

public class RolResponse {
    private int idRol;
    private String nomRol;
    private int nivelRol;

    public RolResponse(int idRol, String nomRol, int nivelRol) {
        this.idRol = idRol;
        this.nomRol = nomRol;
        this.nivelRol = nivelRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNomRol() {
        return nomRol;
    }

    public void setNomRol(String nomRol) {
        this.nomRol = nomRol;
    }

    public int getNivelRol() {
        return nivelRol;
    }

    public void setNivelRol(int nivelRol) {
        this.nivelRol = nivelRol;
    }
}
