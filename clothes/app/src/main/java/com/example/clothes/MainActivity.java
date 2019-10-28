package com.example.clothes;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.calendar.CalendarView;
import com.example.viewclothes.viewclothes;
import com.example.weather.weather;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (TextView) findViewById(R.id.date);
        date.setText(new SimpleDateFormat("yyyy / MM / dd").format(new Date()));
    }

    // 管理衣服
    public void Manageclothes(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , Manageclothes.class);
        startActivity(intent);
    }

    // 預覽穿衣
    public void View_wearing(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , viewclothes.class);
        startActivity(intent);
    }

    // 去行事曆
    public void Calendar(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , CalendarView.class);
        startActivity(intent);
    }

    // 去天氣
    public void Weekweather(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , weather.class);
        startActivity(intent);
    }

    // 去個人設定
    public void Mysetting(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , setting.class);
        startActivity(intent);
    }

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( MainActivity.this  , MainActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }

}

