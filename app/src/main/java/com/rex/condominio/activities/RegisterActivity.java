package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.rex.condominio.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(V -> finish());
    }
}