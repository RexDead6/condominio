package com.rex.condominio.retrofit.response;

public class AnuncioResponse {
    private int idAnu;
    private UsuarioResponse Usuario;
    private String descAnu;
    private String image;
    private String fechaAnu;

    public AnuncioResponse(int idAnu, UsuarioResponse usuario, String descAnu, String image, String fechaAnu) {
        this.idAnu = idAnu;
        Usuario = usuario;
        this.descAnu = descAnu;
        this.image = image;
        this.fechaAnu = fechaAnu;
    }

    public int getIdAnu() {
        return idAnu;
    }

    public void setIdAnu(int idAnu) {
        this.idAnu = idAnu;
    }

    public UsuarioResponse getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        Usuario = usuario;
    }

    public String getDescAnu() {
        return descAnu;
    }

    public void setDescAnu(String descAnu) {
        this.descAnu = descAnu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFechaAnu() {
        return fechaAnu;
    }

    public void setFechaAnu(String fechaAnu) {
        this.fechaAnu = fechaAnu;
    }
}
