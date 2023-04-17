package com.rex.condominio.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

public class TextWatcherCurrency implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    private final int scale;
    private final int decimals;

    public TextWatcherCurrency(EditText editText, int scale) {
        editTextWeakReference = new WeakReference<>(editText);
        this.scale = scale;
        String data = "1";
        for (int i = 0; i<scale; i++){
            data = data+"0";
        }
        decimals = Integer.parseInt(data);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();

        if (editText == null) return;

        String s = editable.toString();
        if (s.isEmpty()) return;

        editText.removeTextChangedListener(this);
        String clearString = s.replaceAll("[$,.]", "");
        BigDecimal total = new BigDecimal(clearString).setScale(scale, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(decimals), BigDecimal.ROUND_FLOOR);

        String part[] =  total.toPlainString().toString().split("\\.");
        String ini = part[0];
        String finL = "";

        while(ini.length() > 3){
            String extrae = ini.substring(ini.length() - 3, ini.length());
            ini =  ini.substring(0, ini.length() - 3);
            finL = "." +  extrae + finL;
        }

        String formatted = ini + finL + "," + part[1];

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}
