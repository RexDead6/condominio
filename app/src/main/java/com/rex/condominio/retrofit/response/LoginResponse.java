package com.rex.condominio.retrofit.response;

public class LoginResponse {
    private int id;
    private int idFam;
    private int statusUsu;
    private RolResponse Rol;

    public LoginResponse(int id, int idFam, int statusUsu, RolResponse rol) {
        this.id = id;
        this.idFam = idFam;
        this.statusUsu = statusUsu;
        Rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFam() {
        return idFam;
    }

    public void setIdFam(int idFam) {
        this.idFam = idFam;
    }

    public int getStatusUsu() {
        return statusUsu;
    }

    public void setStatusUsu(int statusUsu) {
        this.statusUsu = statusUsu;
    }

    public RolResponse getRol() {
        return Rol;
    }

    public void setRol(RolResponse rol) {
        Rol = rol;
    }
}
