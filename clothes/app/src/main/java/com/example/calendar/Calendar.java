package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import com.example.clothes.MainActivity;
import com.example.clothes.R;

public class Calendar extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
    }

    // 新增活動按鈕
    public void AddEvent(View view) {
        Intent intent = new Intent(this, AddSchedule.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass(Calendar.this, MainActivity.class);
        startActivity(intent);
        Calendar.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }
}