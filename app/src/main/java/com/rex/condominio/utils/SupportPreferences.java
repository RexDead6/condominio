package com.rex.condominio.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rex.condominio.R;

public class SupportPreferences extends Application {

    public static final String BASE_URL = "http://192.168.1.100/proyectos/apirest/api/";
    public static final String IMG_URL = "http://192.168.1.100/proyectos/apirest/assets/";
    SharedPreferences mpPreferences;
    private static SupportPreferences instance;

    private final String ARCHIVE_PREFERENCE  = "CONDOMINIO.PREFERENCES";
    public static String TOKEN_PREFERENCE = "CONDOMINIO.TOKEN";

    public static SupportPreferences getInstance(Context context){
        if (instance == null){
            instance = new SupportPreferences(context);
        }
        return instance;
    }

    private SupportPreferences(Context context){
        mpPreferences = context.getSharedPreferences(ARCHIVE_PREFERENCE, context.MODE_PRIVATE);
    }

    public void savePreference(String preference, String value){
        SharedPreferences.Editor editor = mpPreferences.edit();
        editor.putString(preference, value).apply();
    }

    public String getPreference(String preference){
        return mpPreferences.getString(preference, "");
    }

    public static void loadFrament(Fragment fragment, FragmentTransaction trans){
        loadFrament(fragment, trans, true);
    }

    public static void loadFrament(Fragment fragment, FragmentTransaction trans, boolean stack){
        trans.replace(R.id.fragment_container, fragment);
        if (stack) trans.addToBackStack(null);
        trans.commit();
    }
}
