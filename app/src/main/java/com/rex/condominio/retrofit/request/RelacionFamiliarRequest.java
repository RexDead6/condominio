package com.rex.condominio.retrofit.request;

public class RelacionFamiliarRequest {
    private String hash;

    public RelacionFamiliarRequest(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
