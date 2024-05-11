package com.rex.condominio.retrofit.response;

import java.util.ArrayList;

public class StatiticResponse {
    private ArrayList<AnuncioResponse> ultimosAnuncios;
    private ArrayList<ServicioResponse> serviciosPorPagar;
    private ArrayList<MesesResponse> pagosMeses;

    public ArrayList<AnuncioResponse> getUltimosAnuncios() {
        return ultimosAnuncios;
    }

    public void setUltimosAnuncios(ArrayList<AnuncioResponse> ultimosAnuncios) {
        this.ultimosAnuncios = ultimosAnuncios;
    }

    public ArrayList<ServicioResponse> getServiciosPorPagar() {
        return serviciosPorPagar;
    }

    public void setServiciosPorPagar(ArrayList<ServicioResponse> serviciosPorPagar) {
        this.serviciosPorPagar = serviciosPorPagar;
    }

    public ArrayList<MesesResponse> getPagosMeses() {
        return pagosMeses;
    }

    public void setPagosMeses(ArrayList<MesesResponse> pagosMeses) {
        this.pagosMeses = pagosMeses;
    }
}
