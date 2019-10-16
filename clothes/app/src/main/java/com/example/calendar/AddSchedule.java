package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.clothes.MainActivity;
import com.example.clothes.R;



public class AddSchedule extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    // 取消按鈕
    public void cancel(View view) {
        Intent intent = new Intent();
        intent.setClass(AddSchedule.this, Calendar.class);
        startActivity(intent);
        AddSchedule.this.finish();
    }

    // 取消按鈕
    public void stockpile(View view) {
        Intent intent = new Intent();
        intent.setClass(AddSchedule.this, Calendar.class);
        startActivity(intent);
        AddSchedule.this.finish();
    }

}

