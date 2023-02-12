package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.rex.condominio.R;
import com.rex.condominio.activities.register.ActivarUserActivity;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String idUser = SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE);
        if (idUser.equals("")){
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                if (new TokenSupport(SplashActivity.this).getIdFam().equals("00"))
                    intent = new Intent(SplashActivity.this, ActivarUserActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }
    }
}