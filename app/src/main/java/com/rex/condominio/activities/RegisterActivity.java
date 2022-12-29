package com.rex.condominio.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView btn_back;
    private TextInputEditText et_cedula, et_nombre, et_apellido, et_telefono, et_clave, et_clave1;
    private RadioGroup radio_group;
    private RadioButton radio_m, radio_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_back = findViewById(R.id.btn_back);
        et_cedula = findViewById(R.id.et_cedula);
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_telefono = findViewById(R.id.et_telefono);
        radio_group = findViewById(R.id.radio_group);
        radio_m = findViewById(R.id.radio_m);
        radio_f = findViewById(R.id.radio_f);
        et_clave = findViewById(R.id.et_clave);
        et_clave1 = findViewById(R.id.et_clave1);

        btn_back.setOnClickListener(V -> finish());
    }

    public void registrarseButtom(View view){
        if (!validateInputs()) return;


    }

    public boolean validateInputs(){
        boolean success = false;



        return success;
    }
}