package com.rex.condominio.retrofit.response;

public class TokenResponse {
    private String token;
    private boolean isJefe;

    public TokenResponse(String token, boolean isJefe) {
        this.token = token;
        this.isJefe = isJefe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isJefe() {
        return isJefe;
    }

    public void setJefe(boolean jefe) {
        isJefe = jefe;
    }
}
