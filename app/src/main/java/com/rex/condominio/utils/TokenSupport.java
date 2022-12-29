package com.rex.condominio.utils;

import android.content.Context;

public class TokenSupport {
    private String idUsu;
    private String idFam;
    private String idRol;

    public TokenSupport(Context context){
        String[] tokenData = SupportPreferences.getInstance(context).getPreference(SupportPreferences.TOKEN_PREFERENCE).split("|");
        this.idUsu = tokenData[0];
        this.idFam = tokenData[1];
        this.idRol = tokenData[2];
    }

    public String getIdUsu() {
        return idUsu;
    }

    public String getIdFam() {
        return idFam;
    }

    public String getIdRol() {
        return idRol;
    }
}
