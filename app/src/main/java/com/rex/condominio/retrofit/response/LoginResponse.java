package com.rex.condominio.retrofit.response;

public class LoginResponse {
    private int id;
    private RolResponse Rol;

    public LoginResponse(int id, RolResponse rol) {
        this.id = id;
        Rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolResponse getRol() {
        return Rol;
    }

    public void setRol(RolResponse rol) {
        Rol = rol;
    }
}
