package com.rex.condominio.retrofit.response;

public class UsuarioResponse {
    private int idUsu;
    private RolResponse Rol;
    private int statusUsu;
    private String cedUsu;
    private String nomUsu;
    private String apeUsu;
    private String generoUsu;
    private String telUsu;
    private String imgUsu;

    public UsuarioResponse(int idUsu, RolResponse rol, int statusUsu, String cedUsu, String nomUsu, String apeUsu, String generoUsu, String telUsu, String imgUsu) {
        this.idUsu = idUsu;
        Rol = rol;
        this.statusUsu = statusUsu;
        this.cedUsu = cedUsu;
        this.nomUsu = nomUsu;
        this.apeUsu = apeUsu;
        this.generoUsu = generoUsu;
        this.telUsu = telUsu;
        this.imgUsu = imgUsu;
    }

    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }

    public RolResponse getRol() {
        return Rol;
    }

    public void setRol(RolResponse rol) {
        Rol = rol;
    }

    public int getStatusUsu() {
        return statusUsu;
    }

    public void setStatusUsu(int statusUsu) {
        this.statusUsu = statusUsu;
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

    public String getImgUsu() {
        return imgUsu;
    }

    public void setImgUsu(String imgUsu) {
        this.imgUsu = imgUsu;
    }
}
