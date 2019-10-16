package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.clothes.MainActivity;
import com.example.clothes.R;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Calendar extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            // month 預設 0-11
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                Toast.makeText(Calendar.this, "您選的日期是" + year + "年" + (month + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_LONG).show();
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        // 獲取當前時間
        Date date = new Date(System.currentTimeMillis());
        Log.d("test", "date = " +  simpleDateFormat.format(date));
    }


    // 回到今天
    public void today(View view) {

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
