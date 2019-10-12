package com.example.clothes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class editclothes extends AppCompatActivity {
    //傳入衣服id
    public static Long clothesID ;

    //傳入照片
    protected static String PicPath ;

    // 宣告資料庫功能類別欄位變數
    private clothesDAO dao;
    public static boolean load = true;
    String tempDBpic;

    //溫度區間_宣告
    private ArrayList<String> options1Items = new ArrayList<> ();
    private OptionsPickerView pvOptions;
    int flag ;

    String clothesType;
    String clothesSeason = "";
    String[] season = new String[] {"春", "夏", "秋", "冬"};
    boolean spring, summer, autumn, winter = false;
    String[] mClothes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editclothes);
        dao = new clothesDAO(getApplicationContext());
        initData();

        //完成後選項
        final Button editok = (Button)findViewById( R.id.editok);
        final Button delclothes = (Button)findViewById( R.id.delclothes);
        editok.setOnClickListener(EditOrDel);
        delclothes.setOnClickListener(EditOrDel);

        //新增圖片(addclothes)
        ImageView clothesPic = (ImageView)findViewById( R.id.clothesPic);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClothes.clothesID = clothesID;
                Intent intent = new Intent ();
                intent.setClass( editclothes.this  , AddClothes.class);
                startActivity(intent);

            }
        });
        Log.e("PIC",PicPath+" "+load);
        showPic(PicPath);
        if(PicPath != tempDBpic) AddClothes.finishself.finish();
        //溫度區間--定義資料
        final TextView temp_range_upper = (TextView)findViewById( R.id.TempRange_Upper);
        final TextView temp_range_lower = (TextView)findViewById( R.id.TempRange_Lower);
        temp_range_upper.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_upper.getPaint().setAntiAlias(true);//抗鋸齒
        temp_range_lower.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG); //下底線
        temp_range_lower.getPaint().setAntiAlias(true);//抗鋸齒
        temp_range_upper.setOnClickListener(tempChangeListener);
        temp_range_lower.setOnClickListener(tempChangeListener);

        //穿衣季節
        CheckBox Spring = (CheckBox) findViewById( R.id.spring);
        CheckBox Summer = (CheckBox) findViewById( R.id.summer);
        CheckBox Autumn = (CheckBox) findViewById( R.id.autumn);
        CheckBox Winter = (CheckBox) findViewById( R.id.winter);
        Spring.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Summer.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Autumn.setOnCheckedChangeListener(seasonCheckedChangeListener);
        Winter.setOnCheckedChangeListener(seasonCheckedChangeListener);

        //衣服種類選單
        final Spinner spinner = (Spinner)findViewById( R.id.spinner);
        final ArrayAdapter<CharSequence> clothesList = ArrayAdapter.createFromResource( editclothes.this,
                R.array.clothesclass, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(clothesList);
        //衣服類型監聽事件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                clothesType = adapterView.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText( editclothes.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });

        //建立選擇器資料(溫度區間)
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
                .setDividerColor( Color.GREEN)//設定分割線顏色
                .setSelectOptions(120)//默認選中值
                .setBgColor( Color.BLACK)
                .setTitleBgColor( Color.DKGRAY)
                .setTitleColor( Color.LTGRAY)
                .setCancelColor( Color.YELLOW)
                .setSubmitColor( Color.YELLOW)
                .setCancelText("取消")
                .setSubmitText("確定")
                .setTextColorCenter( Color.LTGRAY)
                .setBackgroundId(0x66000000) //設定外部遮罩顏色
                .build();
        pvOptions.setPicker(options1Items);


    }

    //顯示圖片
    public void showPic(String picpath){
        ImageView imv = (ImageView)findViewById( R.id.clothesPic);
        Bitmap bitmap = BitmapFactory.decodeFile(picpath);
        paletteBitmap(pathToBitmap(picpath, 800, 800)); //抓取顏色
        imv.setImageBitmap(bitmap);
    }

    //抓取顏色
    public Bitmap pathToBitmap(String path, int wantWidth, int wantHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置为ture只获取图片大小
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, options); // 返回为空
        int realWidth = options.outWidth;
        int readlHeight = options.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (realWidth > wantWidth || readlHeight > wantHeight) { // 缩放
            scaleWidth = ((float) realWidth) / wantWidth;
            scaleHeight = ((float) readlHeight) / wantHeight;
        }
        options.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        options.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<> ( BitmapFactory.decodeFile(path, options));
        return Bitmap.createScaledBitmap(weak.get(), wantWidth, wantHeight, true);
    }
    private void paletteBitmap(Bitmap bitmap) {
        final ImageView pcolor = (ImageView)findViewById( R.id.imageView3);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //Palette.Swatch swatch = palette.getMutedSwatch();
                Palette.Swatch swatch = palette.getVibrantSwatch();
                //Palette.Swatch swatch = palette.getDarkMutedSwatch();
                //Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                //Palette.Swatch swatch = palette.getLightMutedSwatch();
                //Palette.Swatch swatch = palette.getLightVibrantSwatch();
                if (swatch != null) {
                    pcolor.setBackgroundColor(swatch.getRgb());
                    //pcolor.setBackgroundColor(Color.parseColor("#F90F3D"));
                } else {
                    Log.e("smallsoho", "swatch为空");
                }
            }
        });
    }

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

    private void initData(){
        getClothesMember member = dao.getoneID(clothesID);
        //layout的物件宣告
        TextView clothesname = (TextView)findViewById( R.id.clothesname);
        TextView temp_range_upper = (TextView)findViewById( R.id.TempRange_Upper);
        TextView temp_range_lower = (TextView)findViewById( R.id.TempRange_Lower);
        Spinner spinner = (Spinner)findViewById( R.id.spinner);
        CheckBox Spring = (CheckBox) findViewById( R.id.spring);
        CheckBox Summer = (CheckBox) findViewById( R.id.summer);
        CheckBox Autumn = (CheckBox) findViewById( R.id.autumn);
        CheckBox Winter = (CheckBox) findViewById( R.id.winter);

        //showPic(member.getImgPath());
        if(load) PicPath = member.getImgPath();
        tempDBpic = member.getImgPath();

        clothesname.setText(member.getName());
        temp_range_lower.setText(member.getTempLower().toString() + "℃");
        temp_range_upper.setText(member.getTempUpper().toString() + "℃");
        //Spinner修改預設值成db內容
        mClothes =  getResources().getStringArray(R.array.clothesclass);
        spinner.setSelection(Arrays.asList(mClothes).indexOf(member.getType()), true);

        //season預設內容
        clothesSeason = member.getSeasen();
        for (int i = 1; i<=clothesSeason.length() ; i++){
            for (int j = 0; j<4 ; j++){
                if(season[j].equals(String.valueOf(clothesSeason.charAt(i-1)))){
                    switch (j){
                        case 0:
                            spring = true;
                            Spring.setChecked(true);
                            break;
                        case 1:
                            summer = true;
                            Summer.setChecked(true);
                            break;
                        case 2:
                            autumn = true;
                            Autumn.setChecked(true);
                            break;
                        case 3:
                            winter = true;
                            Winter.setChecked(true);
                            break;
                    }
                }
            }
        }

        //dao.close();
        load = false;
    }
    //完成選項
    private View.OnClickListener EditOrDel = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delclothes:
                    LeaveEdit();
                    dao.delete(clothesID);
                    dao.close();
                    break;
                case R.id.editok:
                    CompleteEDIT();
                    if(clothesSeason != "") LeaveEdit();
                    break;
            }

        }
    };

    //離開編輯畫面
    public void LeaveEdit(){
        Intent intent = new Intent ();
        //回到管理頁面
        Manageclothes.finishself.finish();
        intent.setClass(editclothes.this, Manageclothes.class);
        startActivity(intent);
        finish();
    }

    //整理進入DB
    public void CompleteEDIT(){
        getClothesMember getclothesmember = new getClothesMember();
        //1衣服編號
        getclothesmember.setId(clothesID);
        //2衣服照片(路徑)<<PicPath>>
        getclothesmember.setImgPath(PicPath);

        //3衣服名稱<<clothesName>>
        String clothesName = ((EditText)findViewById( R.id.clothesname)).getText().toString();
        getclothesmember.setName(clothesName);

        //4衣服類型<<clothesType>>
        getclothesmember.setType(clothesType);

        //5溫度下限<<tempLower>>
        String tempL = ((TextView)findViewById( R.id.TempRange_Lower)).getText().toString();
        Long tempLower = Long.parseLong(tempL.substring(0,tempL.indexOf("℃")));
        getclothesmember.setTempLower(tempLower);

        //6溫度上限<<tempUpper>>
        String tempU = ((TextView)findViewById( R.id.TempRange_Upper)).getText().toString();
        Long tempUpper = Long.parseLong(tempU.substring(0,tempU.indexOf("℃")));
        getclothesmember.setTempUpper(tempUpper);

        //7衣服季節<<clothesSeason>>
        clothesSeason = "";
        if (spring) clothesSeason = clothesSeason + "春";
        if (summer) clothesSeason = clothesSeason + "夏";
        if (autumn) clothesSeason = clothesSeason + "秋";
        if (winter) clothesSeason = clothesSeason + "冬";
        getclothesmember.setSeasen(clothesSeason);

        //8建立日期<<timeStamp>>
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        getclothesmember.setUpdateTime(timeStamp);


        if(clothesSeason == ""){
            Toast.makeText( editclothes.this, "請至少選擇一個季節" , Toast.LENGTH_LONG).show();
        }else{
            dao.update(getclothesmember);
            PicPath = null;
            load = true;
            dao.close();
        }
    }
}
