package com.rex.condominio.retrofit.request;

public class UsuarioRequest {
    private String cedUsu;
    private String nomUsu;
    private String apeUsu;
    private String generoUsu;
    private String telUsu;
    private String claveUsu;

    public UsuarioRequest(String cedUsu, String nomUsu, String apeUsu, String generoUsu, String telUsu, String claveUsu) {
        this.cedUsu = cedUsu;
        this.nomUsu = nomUsu;
        this.apeUsu = apeUsu;
        this.generoUsu = generoUsu;
        this.telUsu = telUsu;
        this.claveUsu = claveUsu;
    }

    public String getCedUsu() {
        return cedUsu;
    }

    public void setCedUsu(String cedUsu) {
        this.cedUsu = cedUsu;
    }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public String getApeUsu() {
        return apeUsu;
    }

    public void setApeUsu(String apeUsu) {
        this.apeUsu = apeUsu;
    }

    public String getGeneroUsu() {
        return generoUsu;
    }

    public void setGeneroUsu(String generoUsu) {
        this.generoUsu = generoUsu;
    }

    public String getTelUsu() {
        return telUsu;
    }

    public void setTelUsu(String telUsu) {
        this.telUsu = telUsu;
    }

    public String getClaveUsu() {
        return claveUsu;
    }

    public void setClaveUsu(String claveUsu) {
        this.claveUsu = claveUsu;
    }
}
