package com.deeshop.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextHelper {

    public interface OnTextChangedInterface {
        void afterTextChanged(EditText editText,Editable s);
    }

    public static void bindOnChangeListener(EditText editText, OnTextChangedInterface onTextChangedListener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onTextChangedListener.afterTextChanged(editText,s);
            }
        });
    }

}
