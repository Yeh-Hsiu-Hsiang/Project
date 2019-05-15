package com.example.clothes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;

public class editclothes extends AppCompatActivity {
    //資料庫
    static final String db_name = "clothes";
    static final String tb_name = "ManageClothes";
    SQLiteDatabase db;

    //溫度區間_宣告
    private ArrayList<String> options1Items = new ArrayList<>();
    private OptionsPickerView pvOptions;
    int flag ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editclothes);

        //衣服種類選單
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> clothesList = ArrayAdapter.createFromResource(editclothes.this,
                R.array.clothesclass, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(clothesList);

        //溫度區間--定義資料
        final TextView temp_range_upper = (TextView)findViewById(R.id.TempRange_Upper);
        final TextView temp_range_lower = (TextView)findViewById(R.id.TempRange_Lower);
        temp_range_upper.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_upper.getPaint().setAntiAlias(true);//抗鋸齒
        temp_range_lower.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_lower.getPaint().setAntiAlias(true);//抗鋸齒

        initOptionData();
        //建立選擇器
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1) ;
                if (flag == 1)
                    temp_range_lower.setText(tx);
                else if(flag == 2)
                    temp_range_upper.setText(tx);
                flag = 3;
            }
        }).setTitleText("請選擇溫度區間") // 選擇器標題
                .setContentTextSize(20)//設定滾輪文字大小
                .setDividerColor(Color.GREEN)//設定分割線顏色
                .setSelectOptions(120)//默認選中值
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setCancelText("取消")
                .setSubmitText("確定")
                .setTextColorCenter(Color.LTGRAY)
                .setBackgroundId(0x66000000) //設定外部遮罩顏色
                .build();

        pvOptions.setPicker(options1Items);
        //溫度區間--跳出選擇器(下限)
        temp_range_lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.TempRange_Lower && pvOptions != null) {
                    flag = 1;
                    pvOptions.show(); // 從下方彈出選擇器
                }
                String str = temp_range_lower.getText().toString();
                Log.e("tag", str.substring(0,str.indexOf("℃")));
            }
        });
        //溫度區間--跳出選擇器(上限)
        temp_range_upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.TempRange_Upper && pvOptions != null) {
                    flag = 2;
                    pvOptions.show(); // 從下方彈出選擇器
                }
            }
        });
       // OpenDB();
    }

    public void OpenDB(){
        db = openOrCreateDatabase(db_name, MODE_PRIVATE , null);


    }

    private void initOptionData() {
        //選項1
        for (int i = -100; i < 100; i++) {
            options1Items.add( String.valueOf(i) + "℃");
        }
    }


}
