package com.example.item_sales.basicutality;

import android.app.DatePickerDialog;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;

public class BasicUtality extends AppCompatActivity {

    public static String TAG = BasicUtality.class.getName();
    final Calendar myCalendar = Calendar.getInstance();
    public static BasicUtality basicUtality = null;

    private BasicUtality() {
    }

    public static BasicUtality getInstance() {
        if (basicUtality == null) {
            basicUtality = new BasicUtality();
        }
        return basicUtality;
    }

    public DatePickerDialog getDatePicker(DatePickerDialog.OnDateSetListener datePickerListener, boolean isTrue, String title) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(this,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePicker.setCancelable(isTrue);
        datePicker.setTitle(title);

        return datePicker;
    }

}
