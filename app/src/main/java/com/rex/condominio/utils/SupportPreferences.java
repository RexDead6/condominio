package com.rex.condominio.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rex.condominio.R;

public class SupportPreferences extends Application {

    public static final String BASE_URL = "http://192.168.1.100/proyectos/apirest/api/";
    SharedPreferences mpPreferences;
    private static SupportPreferences instance;

    private final String PREFERENCE_ARCHIVE  = "CONDOMINIO.PREFERENCES";
    public static final String USER_ID_PREFERENCE  = "CONDIMINIO.USER_ID";
    public static final String FAM_ID_PREFERENCE  = "CONDIMINIO.FAM_ID";

    public static SupportPreferences getInstance(Context context){
        if (instance == null){
            instance = new SupportPreferences(context);
        }
        return instance;
    }

    private SupportPreferences(Context context){
        mpPreferences = context.getSharedPreferences(PREFERENCE_ARCHIVE, context.MODE_PRIVATE);
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
