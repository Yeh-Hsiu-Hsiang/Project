package com.example.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.clothes.R;


public class AddSchedule extends AppCompatActivity {

    private Switch swh_status;
    boolean is_enable = true;

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

