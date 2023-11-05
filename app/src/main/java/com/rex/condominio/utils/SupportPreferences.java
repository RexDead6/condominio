package com.rex.condominio.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rex.condominio.R;
import com.rex.condominio.fragments.activarUser.QrScannerFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SupportPreferences extends Application {

    public static final String BASE_URL = "http://192.168.1.118:8000/";
    // public static final String BASE_URL = "http://condominio.alwaysdata.net/";
    public static final String BASE_URL_ASSETS = BASE_URL+"src/assets/";
    private static SupportPreferences instance;
    private SharedPreferences mpPreferences;
    private final String ARCHIVE_PREFERENCE = "CONDOMINIO.PREFERENCES";
    public static String TOKEN_PREFERENCE = "CONDOMINIO.TOKEN";
    public static String JEFE_FAMILIA_PREFERENCE = "CONDOMINIO.JEFE_FAMILIA";
    public static String COMUNIDAD_ACTUAL_PREFERENCE = "CONDOMINIO.COMUNIDAD_ACTUAL";
    public static String COMUNIDAD_ACTUAL_ADMIN_PREFERENCE = "CONDOMINIO.COMUNIDAD_ACTUAL_ADMIN";
    public static String NOTIFICACION_PREFERENCE = "CONDOMINIO.NOTIFICACION";
    public static String FAMILIA_LIST_OBJECT = "CONDOMINIO.OBJECT.FAMILIA";
    public static String ADMIN_COMUNIDAD_PREFERENCE = "CONDOMINIO.ADMIN_COMUNIDAD";

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

    public static String formatCurrency(float currency){
        NumberFormat tf = NumberFormat.getCurrencyInstance();
        return tf.format(currency).replace("$", "");
    }

    public static String formatDate(String date){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        }catch (Exception e){
            Log.e("FormatDateNoParseable", date);
            return date;
        }
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static File saveImage(Context context, Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory()+ File.separator + "DCIM" + File.separator + "Camera");
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdir();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
