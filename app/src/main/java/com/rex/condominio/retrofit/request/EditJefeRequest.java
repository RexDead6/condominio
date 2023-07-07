package com.rex.condominio.retrofit.request;

public class EditJefeRequest {
    private int idJefeFamilia;
    private int idFam;

    public EditJefeRequest(int idJefeFamilia, int idFam) {
        this.idJefeFamilia = idJefeFamilia;
        this.idFam = idFam;
    }

    public int getIdJefeFamilia() {
        return idJefeFamilia;
    }

    public void setIdJefeFamilia(int idJefeFamilia) {
        this.idJefeFamilia = idJefeFamilia;
    }

    public int getIdFam() {
        return idFam;
    }

    public void setIdFam(int idFam) {
        this.idFam = idFam;
    }
}
