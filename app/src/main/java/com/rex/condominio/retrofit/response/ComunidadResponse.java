package com.rex.condominio.retrofit.response;

import androidx.annotation.NonNull;

public class ComunidadResponse {
    private int idUrb;
    private String nomUrb;
    private String direccion;
    private int status;
    private boolean isAdmin;
    private int totalFamilias;


    // Getter Methods

    public int getIdUrb() {
        return idUrb;
    }

    public String getNomUrb() {
        return nomUrb;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getStatus() {
        return status;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getTotalFamilias() {
        return totalFamilias;
    }

    // Setter Methods

    public void setIdUrb( int idUrb ) {
        this.idUrb = idUrb;
    }

    public void setNomUrb( String nomUrb ) {
        this.nomUrb = nomUrb;
    }

    public void setDireccion( String direccion ) {
        this.direccion = direccion;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setTotalFamilias(int totalFamilias) {
        this.totalFamilias = totalFamilias;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nomUrb;
    }
}
