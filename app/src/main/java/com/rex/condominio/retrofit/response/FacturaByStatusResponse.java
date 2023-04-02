package com.rex.condominio.retrofit.response;

import java.util.ArrayList;

public class FacturaByStatusResponse {

    private ArrayList<FacturaResponse> pendientes;
    private ArrayList<FacturaResponse> confirmadas;
    private ArrayList<FacturaResponse> canceladas;

    public FacturaByStatusResponse(ArrayList<FacturaResponse> pendientes, ArrayList<FacturaResponse> confirmadas, ArrayList<FacturaResponse> canceladas) {
        this.pendientes = pendientes;
        this.confirmadas = confirmadas;
        this.canceladas = canceladas;
    }

    public ArrayList<FacturaResponse> getPendientes() {
        return pendientes;
    }

    public void setPendientes(ArrayList<FacturaResponse> pendientes) {
        this.pendientes = pendientes;
    }

    public ArrayList<FacturaResponse> getConfirmadas() {
        return confirmadas;
    }

    public void setConfirmadas(ArrayList<FacturaResponse> confirmadas) {
        this.confirmadas = confirmadas;
    }

    public ArrayList<FacturaResponse> getCanceladas() {
        return canceladas;
    }

    public void setCanceladas(ArrayList<FacturaResponse> canceladas) {
        this.canceladas = canceladas;
    }
}
