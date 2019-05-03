package com.example.clothes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manageclothes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageclothes);
                //去新增衣服(頁面)
        Button addclothes = (Button)findViewById(R.id.Addclothes);
        addclothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Manageclothes.this  , AddClothes.class);
                startActivity(intent);
            }
        });
    }
}
