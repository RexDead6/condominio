package com.rex.condominio.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.rex.condominio.R;
import com.rex.condominio.utils.OnClickResponse;

public class ConfirmYesNoDialog extends Dialog {

    private TextView tv_message;
    private MaterialButton btn_confirmar, btn_cancelar;

    public ConfirmYesNoDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.modal_yes_not_confirm);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tv_message = findViewById(R.id.tv_message);
        btn_confirmar = findViewById(R.id.btn_confirmar);
        btn_cancelar = findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(V -> dismiss());
    }

    public ConfirmYesNoDialog setMessage(String msg){
        tv_message.setText(msg);
        return this;
    }

    public ConfirmYesNoDialog setPositiveButtom(OnClickResponse<ConfirmYesNoDialog> onClickResponse){
        btn_confirmar.setOnClickListener(V -> onClickResponse.onClick(this));
        return this;
    }
}