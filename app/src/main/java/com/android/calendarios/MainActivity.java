package com.android.calendarios;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
//s
//        android.widget.DatePicker datePicker= findViewById(R.id.datepickertest);
//        datePicker.setSpinnersShown(false);
        findViewById(R.id.datepickertest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker= new DatePicker(MainActivity.this,0);
        }});
//



    }
}
