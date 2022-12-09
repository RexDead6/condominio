package com.rex.condominio.retrofit;

import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private RequestInterface requestInterface;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SupportPreferences.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestInterface = retrofit.create(RequestInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public RequestInterface getRequestInterface(){
        return requestInterface;
    }
}
