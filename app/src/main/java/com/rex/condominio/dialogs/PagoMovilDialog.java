package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rex.condominio.R;
import com.rex.condominio.retrofit.response.PagoMovilResponse;

public class PagoMovilDialog extends Dialog {

    private PagoMovilResponse pagoMovil;
    private TextView tv_cedula, tv_telefono, tv_banco;

    public PagoMovilDialog(@NonNull Context context, PagoMovilResponse pagoMovil) {
        super(context);
        this.pagoMovil = pagoMovil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pago_movil);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tv_cedula = findViewById(R.id.tv_cedula);
        tv_telefono = findViewById(R.id.tv_telefono);
        tv_banco = findViewById(R.id.tv_banco);

        tv_cedula.setText(pagoMovil.getCedPmv());
        tv_telefono.setText(pagoMovil.getTelPmv());
        tv_banco.setText(pagoMovil.getBanco().getNomBan());
    }
}
