package com.rex.condominio.retrofit.response;

import java.util.ArrayList;

public class FamiliaResponse {
    private int idFam;
    private String descFam;
    private String hashFam;
    private String direccion;
    private ArrayList<UsuarioResponse> users;

    public FamiliaResponse(int idFam, String descFam, String hashFam, String direccion, ArrayList<UsuarioResponse> users) {
        this.idFam = idFam;
        this.descFam = descFam;
        this.hashFam = hashFam;
        this.direccion = direccion;
        this.users = users;
    }

    public int getIdFam() {
        return idFam;
    }

    public void setIdFam(int idFam) {
        this.idFam = idFam;
    }

    public String getDescFam() {
        return descFam;
    }

    public void setDescFam(String descFam) {
        this.descFam = descFam;
    }

    public String getHashFam() {
        return hashFam;
    }

    public void setHashFam(String hashFam) {
        this.hashFam = hashFam;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<UsuarioResponse> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UsuarioResponse> users) {
        this.users = users;
    }
}
