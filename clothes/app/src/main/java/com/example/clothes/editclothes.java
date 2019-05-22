package com.example.clothes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class editclothes extends AppCompatActivity {
    //傳入照片
    protected static String PicPath;
    //資料庫
    static final String db_name = "clothes.db";
    static final String tb_name = "ManageClothes";
    SQLiteDatabase db;

    //溫度區間_宣告
    private ArrayList<String> options1Items = new ArrayList<>();
    private OptionsPickerView pvOptions;
    int flag ;

    String clothesType;
    boolean spring, summer, autumn, winter = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editclothes);
        //完成後選項
        final Button Leave = (Button)findViewById(R.id.leave);
        final Button backAdd = (Button)findViewById(R.id.keepadd);
        Leave.setOnClickListener(LeaveOrAdd);
        backAdd.setOnClickListener(LeaveOrAdd);

        //溫度區間--定義資料
        final TextView temp_range_upper = (TextView)findViewById(R.id.TempRange_Upper);
        final TextView temp_range_lower = (TextView)findViewById(R.id.TempRange_Lower);
        temp_range_upper.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_upper.getPaint().setAntiAlias(true);//抗鋸齒
        temp_range_lower.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_lower.getPaint().setAntiAlias(true);//抗鋸齒
        temp_range_upper.setOnClickListener(tempChangeListener);
        temp_range_lower.setOnClickListener(tempChangeListener);

        //穿衣季節
        CheckBox Spring = (CheckBox) findViewById(R.id.spring);
        CheckBox Summer = (CheckBox) findViewById(R.id.summer);
        CheckBox Autumn = (CheckBox) findViewById(R.id.autumn);
        CheckBox Winter = (CheckBox) findViewById(R.id.winter);
        Spring.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Summer.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Autumn.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Winter.setOnCheckedChangeListener(seasonCheckedChangeListener);

        //顯示照片
        showPic(PicPath);

        //衣服種類選單
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> clothesList = ArrayAdapter.createFromResource(editclothes.this,
                R.array.clothesclass, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(clothesList);
        //衣服類型監聽事件
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                clothesType = adapterView.getSelectedItem().toString();
                //Toast.makeText(editclothes.this, "您選擇"+adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(editclothes.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });


        //建立選擇器資料
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

        OpenDB();
    }

    public void showPic(String picpath){
        ImageView imv = (ImageView)findViewById(R.id.clothesPic);
        Bitmap bitmap = BitmapFactory.decodeFile(picpath);

        imv.setImageBitmap(bitmap);
    }

    private View.OnClickListener LeaveOrAdd = new View.OnClickListener() {
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            CompleteAdd();
            switch (v.getId()){
                case R.id.leave:
                    //回到管理頁面
                    AddClothes.finishself.finish();
                    Manageclothes.finishself.finish();
                    intent.setClass(editclothes.this  , Manageclothes.class);
                    startActivity(intent);
                    editclothes.this.finish();

                    break;
                case R.id.keepadd:
                    //回到新增衣服頁面
                    AddClothes.finishself.finish();
                    //Manageclothes.finishself.finish();
                    intent.setClass(editclothes.this  , AddClothes.class);
                    startActivity(intent);
                    editclothes.this.finish();

                    break;
            }
        }
    };
    //溫度區間監聽事件
    private View.OnClickListener tempChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.TempRange_Lower:
                    if (pvOptions != null){
                        flag = 1;
                        pvOptions.show(); // 從下方彈出選擇器
                    }
                    break;
                case R.id.TempRange_Upper:
                    if (pvOptions != null){
                        flag = 2;
                        pvOptions.show(); // 從下方彈出選擇器
                    }
                    break;
            }
        }
    };

    private void initOptionData() {
        //穿衣溫度-選項
        for (int i = -100; i < 100; i++) {
            options1Items.add( String.valueOf(i) + "℃");
        }
    }
    //穿衣季節監聽事件
    private CompoundButton.OnCheckedChangeListener seasonCheckedChangeListener = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.spring:
                    if(isChecked)
                        spring = true;
                    else
                        spring = false;
                    break;
                case R.id.summer:
                    if(isChecked)
                        summer = true;
                    else
                        summer = false;
                    break;
                case R.id.autumn:
                    if(isChecked)
                        autumn = true;
                    else
                        autumn = false;
                    break;
                case R.id.winter:
                    if(isChecked)
                        winter = true;
                    else
                        winter = false;
                    break;
            }
        }


    };
    //整理
    public void CompleteAdd(){

        //1衣服編號

        //2衣服照片(路徑)<<PicPath>>
        Log.e("CompleteAdd",PicPath);

        //3衣服名稱<<clothesName>>
        String clothesName = ((EditText)findViewById(R.id.editText)).getText().toString();
        Log.e("CompleteAdd",clothesName);

        //4衣服類型<<clothesType>>
        Log.e("CompleteAdd",clothesType);

        //5溫度下限<<tempLower>>
        String tempL = ((TextView)findViewById(R.id.TempRange_Lower)).getText().toString();
        Integer tempLower =Integer.parseInt(tempL.substring(0,tempL.indexOf("℃")));
        Log.e("CompleteAdd",tempLower.toString());

        //6溫度上限<<tempUpper>>
        String tempU = ((TextView)findViewById(R.id.TempRange_Upper)).getText().toString();
        Integer tempUpper =Integer.parseInt(tempU.substring(0,tempU.indexOf("℃")));
        Log.e("CompleteAdd",tempUpper.toString());

        //7衣服季節<<clothesSeason>>
        String clothesSeason = "";
        if (spring) clothesSeason =clothesSeason + "春";
        if (summer) clothesSeason =clothesSeason + "夏";
        if (autumn) clothesSeason =clothesSeason + "秋";
        if (winter) clothesSeason =clothesSeason + "冬";
        Log.e("CompleteAdd",clothesSeason);

        //8建立日期<<timeStamp>>
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Log.e("CompleteAdd",timeStamp);

        addData(PicPath, clothesName, clothesType, tempLower, tempUpper, clothesSeason, timeStamp);
        db.close();
    }



    public void OpenDB(){
        //開啟或建立資料庫
        db = openOrCreateDatabase(db_name, MODE_PRIVATE , null);
        String createTable = "CREATE TABLE IF NOT EXISTS "+
                tb_name +
                "(衣服編號  INTEGER primary key autoincrement , " +
                "衣服照片 TEXT NOT NULL , " +
                "衣服名稱 TEXT , "+
                "衣服類型  TEXT NOT NULL , " +
                "穿衣溫度_下限 INTEGER NOT NULL , " +
                "穿衣溫度_上限 INTEGER NOT NULL , " +
                "季節 TEXT , "+
                "建立日期 INTEGER NOT NULL ) " ;
        db.execSQL(createTable);
    }

    //加入db資料
    private void addData(String picPath, String clothesName, String clothesType, Integer tempLower, Integer tempUpper,
                         String clothesSeason, String timeStamp){
        ContentValues values = new ContentValues();
        //values.put("衣服編號",);
        values.put("衣服照片", picPath);
        values.put("衣服名稱", clothesName);
        values.put("衣服類型", clothesType);
        values.put("穿衣溫度_下限", tempLower);
        values.put("穿衣溫度_上限", tempUpper);
        values.put("季節", clothesSeason);
        values.put("建立日期", timeStamp);

        db.insert(tb_name,null,values);

    }

}
