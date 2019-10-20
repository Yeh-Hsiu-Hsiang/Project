package com.example.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import com.example.clothes.R;

public class AddSchedule extends AppCompatActivity {

    private Switch swh_status;
    private Button start_day;
    private Button end_day;
    private Button start_time;
    private Button end_time;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        swh_status = (Switch) findViewById(R.id.swh_status);
        swh_status.setChecked(false);
        swh_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Log.d("swh = ", "On");
                   //顯示所選日期
                } else {
                    Log.d("swh = ", "Off");
                    //顯示所選日期範圍
                }
            }
        });

        // 選擇清單
        Spinner spinner = (Spinner)findViewById(R.id.reSpinner);
        final String[] CityArray = {"不重複", "每日", "每周", "每月", "每年"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(AddSchedule.this,
                android.R.layout.simple_spinner_dropdown_item,
                CityArray);
        spinner.setAdapter(lunchList);
    }

    // 取消按鈕
    public void cancel(View view) {
        Intent intent = new Intent();
        intent.setClass(AddSchedule.this, Calendar.class);
        startActivity(intent);
        AddSchedule.this.finish();
    }

    // 確認按鈕
    public void stockpile(View view) {
        Intent intent = new Intent();
        intent.setClass(AddSchedule.this, Calendar.class);
        startActivity(intent);
        AddSchedule.this.finish();
    }

}

