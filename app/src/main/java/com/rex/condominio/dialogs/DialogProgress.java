package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.rex.condominio.R;

public class DialogProgress extends Dialog {
    public DialogProgress(@NonNull Context context) {
        super(context);
        onCreate();
    }

    private void onCreate(){
        setContentView(R.layout.modal_espera);
        setCancelable(false);

    }
}
