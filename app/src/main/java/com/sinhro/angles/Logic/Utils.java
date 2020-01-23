package com.sinhro.angles.Logic;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Utils {
    public static void setTextChangedListener(EditText editText, final MyConsumer<Float> text){
        editText.addTextChangedListener(new TextWatcher() {
            CharSequence recovered;
            boolean shouldReset = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                recovered = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() != 0)
                    try{
                        float val = Float.valueOf(s.toString());
                        if (val < 0)
                            throw new Exception();
                        text.accept(val);
                    }catch (Exception e){
                        shouldReset = true;
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (shouldReset) {
                    s.clear();
                    shouldReset = false;
                    s.append(recovered);
                }
            }
        });
    }
}
