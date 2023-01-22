package com.rex.condominio.retrofit.response;

import java.util.ArrayList;

public class PushMessageResponse {
    private NotificacionesResponse all;
    private ArrayList<NotificacionesResponse> user;

    public PushMessageResponse(NotificacionesResponse all, ArrayList<NotificacionesResponse> user) {
        this.all = all;
        this.user = user;
    }

    public NotificacionesResponse getAll() {
        return all;
    }

    public void setAll(NotificacionesResponse all) {
        this.all = all;
    }

    public ArrayList<NotificacionesResponse> getUser() {
        return user;
    }

    public void setUser(ArrayList<NotificacionesResponse> user) {
        this.user = user;
    }
}
