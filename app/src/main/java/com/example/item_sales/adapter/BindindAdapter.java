package com.example.item_sales.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;

public class BindindAdapter {

    @BindingAdapter("isCheckedValue")
    public static void isCheckedValue(CompoundButton compoundButton, boolean isChecked) {
        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }

    @BindingAdapter("editTextOnChanged")
    public static void editTextOnChanged(EditText editText, String k) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("s", String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @BindingAdapter("isVisible")
    public static void isVisible(View view, boolean isVisible) {
        if (isVisible == true) {
            view.setVisibility(View.VISIBLE);
        } else if (isVisible == false) {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("isEnable")
    public static void isEnable(View view, boolean isEnable) {
        if (isEnable == true) {
            view.setEnabled(true);
        } else if (isEnable == false) {
            view.setEnabled(false);
        }
    }

    @BindingAdapter("setText")
    public static void setText(AppCompatTextView view, double amount) {
        view.setText(String.valueOf(amount));
    }

    @BindingAdapter("setEditText")
    public static void setEditText(AppCompatEditText view, double amount) {
        view.setText(String.valueOf(amount));
    }

}
