package com.rex.condominio.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rex.condominio.R;
import com.rex.condominio.fragments.activarUser.QrScannerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupportPreferences extends Application {

    public static final String BASE_URL = "http://192.168.1.146/proyectos/apirest/api/";
    public static final String IMG_URL = "http://192.168.1.146/proyectos/apirest/assets/";
    private static SupportPreferences instance;
    private SharedPreferences mpPreferences;

    private final String ARCHIVE_PREFERENCE = "CONDOMINIO.PREFERENCES";
    public static String TOKEN_PREFERENCE = "CONDOMINIO.TOKEN";
    public static String NOTIFICACION_PREFERENCE = "CONDOMINIO.NOTIFICACION";
    public static String FAMILIA_LIST_OBJECT = "CONDOMINIO.OBJECT.FAMILIA";

    public static SupportPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new SupportPreferences(context);
        }
        return instance;
    }

    private SupportPreferences(Context context) {
        mpPreferences = context.getSharedPreferences(ARCHIVE_PREFERENCE, context.MODE_PRIVATE);
    }

    public void savePreference(String preference, String value) {
        SharedPreferences.Editor editor = mpPreferences.edit();
        editor.putString(preference, value).apply();
    }

    public String getPreference(String preference) {
        return mpPreferences.getString(preference, "");
    }

    public void savePreferenceInt(String preference, int value) {
        SharedPreferences.Editor editor = mpPreferences.edit();
        editor.putInt(preference, value).apply();
    }

    public int getPreferenceInt(String preference) {
        return mpPreferences.getInt(preference, 0);
    }

    public static class ObjectPreference<T>{
        public void saveObject(Context context, ArrayList<T> object, String preference){
            String json = new Gson().toJson(object, new TypeToken<ArrayList<T>>() {}.getType());
            SharedPreferences.Editor editor = context.getSharedPreferences("CONDOMINIO.PREFERENCES", context.MODE_PRIVATE).edit();
            editor.putString(preference, json).apply();
        }

        public List<T> getObject(Context context, String preference, Class<T[]> clazz){
            String json = context.getSharedPreferences("CONDOMINIO.PREFERENCES", context.MODE_PRIVATE).getString(preference, "");
            T[] arr = new Gson().fromJson(json, clazz);
            return Arrays.asList(arr);
        }
    }

    public static void loadFrament(Fragment fragment, FragmentTransaction trans, boolean stack, int container) {
        loadFrament(fragment, trans, stack, container, true);
    }

    public static void loadFrament(Fragment fragment, FragmentTransaction trans, boolean stack, int container, boolean anim) {
        if (anim) {
            fragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, true));
            fragment.setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, false));
        }
        trans.replace(container, fragment);
        if (stack) trans.addToBackStack(null);
        trans.commit();
    }
}
