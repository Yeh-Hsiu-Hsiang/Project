package com.example.clothes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.calendar.Calendar;
import com.example.viewclothes.viewclothes;
import com.example.weather.weather;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        intent.setClass( MainActivity.this  , Calendar.class);
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

}



