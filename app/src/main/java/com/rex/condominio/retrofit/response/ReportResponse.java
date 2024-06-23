package com.rex.condominio.retrofit.response;

public class ReportResponse {
    String name;

    public ReportResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
