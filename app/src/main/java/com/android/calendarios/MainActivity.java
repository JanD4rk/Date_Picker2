package com.android.calendarios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.mylibrary.DatePicker;
import com.android.mylibrary.LibraryDialog;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
//ss
//        android.widget.DatePicker datePicker= findViewById(R.id.datepickertest);
//        datePicker.setSpinnersShown(false);
        findViewById(R.id.datepickertest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker= new DatePicker(MainActivity.this,0);

                datePicker.setConfirmListener(new LibraryDialog.ConfirmListener() {
                    @Override
                    public void onConfirm(int year, int month, int day) {
                        super.onConfirm(year, month, day);
                    }

                    @Override
                    public void onConfirm(long difference) {
                        super.onConfirm(difference);
                    }

                    @Override
                    public void onConfirm(Calendar calendar) {
                        super.onConfirm(calendar);
                    }
                })
            ;
//



    }});

    }

}

