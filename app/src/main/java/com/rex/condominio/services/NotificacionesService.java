package com.rex.condominio.services;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.rex.condominio.R;
import com.rex.condominio.activities.MainActivity;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.PushMessageResponse;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesService extends Service {

    private final String CHANNEL_ID = "CHANNEL.GENERAL";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        // CHANNEL NOTFICIATION
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "CANAL_GENERAL",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Call<ResponseClient<PushMessageResponse>> call = RetrofitClient.getInstance().getRequestInterface().getNotificaciones(
                SupportPreferences.getInstance(getApplicationContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE)
        );
        call.enqueue(new Callback<ResponseClient<PushMessageResponse>>() {
            @Override
            public void onResponse(Call<ResponseClient<PushMessageResponse>> call, Response<ResponseClient<PushMessageResponse>> response) {
                if (response.code() == 200){
                    if (SupportPreferences.getInstance(getApplicationContext()).getPreferenceInt(SupportPreferences.NOTIFICACION_PREFERENCE) < response.body().getData().getAll().getIdNot()){
                        launchNotification(
                                response.body().getData().getAll().getTipoNot(),
                                response.body().getData().getAll().getTituloNot(),
                                response.body().getData().getAll().getDescNot(),
                                response.body().getData().getAll().getIdNot()
                        );
                        SupportPreferences.getInstance(getApplicationContext()).savePreferenceInt(
                                SupportPreferences.NOTIFICACION_PREFERENCE,
                                response.body().getData().getAll().getIdNot()
                        );
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseClient<PushMessageResponse>> call, Throwable t) {
                Log.e("getNotificaciones", t.toString());
            }
        });

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(),
                1,
                restartService,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);
    }

    private void launchNotification(String className, String titulo, String descripcion, int notId) {
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName(className).getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(notId, builder.build());
    }
}