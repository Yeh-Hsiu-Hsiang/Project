package com.example.viewclothes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothes.MainActivity;
import com.example.clothes.R;
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;
import com.example.clothes.database.weatherDAO;
import com.example.viewclothes.fragment.clothes1Fragment;
import com.example.viewclothes.fragment.clothes2Fragment;
import com.example.viewclothes.fragment.clothes3Fragment;
import com.example.viewclothes.fragment.clothes4Fragment;
import com.example.viewclothes.fragment.clothes5Fragment;
import com.example.viewclothes.fragment.clothes6Fragment;
import com.example.viewclothes.fragment.clothes7Fragment;
import com.example.viewclothes.fragment.clothes8Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class viewclothes extends AppCompatActivity {

    // 宣告資料庫功能類別欄位變數
    private static clothesDAO dao;
    private static weatherDAO weatherDAO;

    private static DrawingView mView;
    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one, R.drawable.selector_two, R.drawable.selector_three,
            R.drawable.selector_four, R.drawable.selector_five, R.drawable.selector_six,
            R.drawable.selector_seven, R.drawable.selector_eight};
    private TextView hinttext;
    public static ArrayList wearlist = new ArrayList<>();

    Integer PoP,Tem;
    Set<String> wearSet = new HashSet<String>();
    SharedPreferences WEAR;
    //定義getClothesMember的集合(上衣下衣)
    private ArrayList<getClothesMember> UPclothes ,DOWNclothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclothes);
        mView = findViewById(R.id.drawingView);
        dao = new clothesDAO(getApplicationContext());
        weatherDAO = new weatherDAO(getApplicationContext());
        WEAR = getSharedPreferences("WEAR", MODE_PRIVATE);
        initdata();
        for(int i=0 ;i < wearlist.size() ; i++){
            getpicture((Long) wearlist.get(i),getApplicationContext());
        }
        processView();
    }

    public void initdata(){
        //抓取天氣db
        try {
            Tem = Integer.valueOf(weatherDAO.getWDweather(weatherDAO.getoneID(1).getNowCity()).get(0).getTemperature());
        }catch (StringIndexOutOfBoundsException e){
            Toast.makeText( viewclothes.this, "獲取天氣資料錯誤" , Toast.LENGTH_LONG).show();
        }
        try {
            PoP = Integer.valueOf(weatherDAO.getWDweather(weatherDAO.getoneID(1).getNowCity()).get(0).getPoPh());
        }catch (StringIndexOutOfBoundsException e){
            Toast.makeText( viewclothes.this, "獲取天氣資料錯誤" , Toast.LENGTH_LONG).show();
        }

        //抓取db資料
        int temp = 0;
        do{
            UPclothes = dao.getUPclothes(Tem+temp , Tem-temp);
            temp++;
            if(temp == 200){
                Toast.makeText( viewclothes.this, "請先至少新增一件上衣" , Toast.LENGTH_LONG).show();
                break;
            }
        }while (UPclothes ==null);
        temp = 0;
        do {
            DOWNclothes = dao.getDOWNclothes(Tem+temp , Tem-temp);
            temp++;
            if(temp == 200){
                Toast.makeText( viewclothes.this, "請先至少新增一件下衣" , Toast.LENGTH_LONG).show();
                break;
            }
        }while (DOWNclothes == null);

        SharedPreferences DATE = getSharedPreferences("DATE", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        if(!timeStamp.equals(DATE.getString("todayis",null)) ||
                Tem != DATE.getInt("todayTem",0) ||
                !String.valueOf(dao.getlastID().get(0).getId()).equals(DATE.getString("Lastclothes",null)) ||
                dao.getUPCount() != DATE.getInt("getUPCount",0) ||
                dao.getDOWNCount() != DATE.getInt("getDOWNCount",0)){
            //修改成今天日期跟現在溫度
            DATE.edit().clear();
            DATE.edit().remove("todayis");
            DATE.edit().remove("Lastclothes");
            DATE.edit().remove("todayTem");
            DATE.edit().remove("getUPCount");
            DATE.edit().remove("getDOWNCount");
            DATE.edit()
                    .putString("todayis",timeStamp)
                    .putString("Lastclothes", dao.getlastID().get(0).getId().toString())
                    .putInt("todayTem", Tem)
                    .putInt("getUPCount", dao.getUPCount())
                    .putInt("getDOWNCount", dao.getDOWNCount())
                    .commit();

            if(wearlist != null)wearlist = null;
            wearlist = new ArrayList<>();
            //決定衣服
            int index = (int)(Math.random() * UPclothes.size());
            if(!UPclothes.get(index).getType().equals("連身裙")){
                wearlist.add(UPclothes.get(index).getId());
                //花上衣+素下衣
                if(!UPclothes.get(index).getStyle().equals("素色") && !UPclothes.get(index).getStyle().equals("圖案或文字")){
                    index = (int) (Math.random() * DOWNclothes.size());
                    while (  !DOWNclothes.get(index).getStyle().equals("格子") ||
                            !DOWNclothes.get(index).getStyle().equals("條紋") ||
                            !DOWNclothes.get(index).getStyle().equals("花紋") ) {
                        if(DOWNclothes.size() == 1) break; //如果有很前衛的人(花上+花下)
                        DOWNclothes.remove(index);
                        index = (int) (Math.random() * DOWNclothes.size());
                    }
                    wearlist.add(DOWNclothes.get(index).getId());
                }else{ //素上衣+任意下衣
                    index = (int) (Math.random() * DOWNclothes.size());
                    wearlist.add(DOWNclothes.get(index).getId());
                }
            }else{ //是連衣直接加入
                wearlist.add(UPclothes.get(index).getId());
            }
        }else {
            if(wearlist != null)wearlist = null;
            wearlist = new ArrayList<>();
            wearSet = WEAR.getStringSet("wearSet",null);
            if(wearSet.size() > 0){
                String[] data = (String[]) wearSet.toArray(new String[wearSet.size()]);
                for (int i = 0; i < data.length; i++){
                    wearlist.add(Long.parseLong(data[i]));
                }
            }
        }
    }

    public void processView(){
        hinttext = findViewById(R.id.hint);
        try {
            if(PoP >= 50)
                hinttext.setText("降雨機率為" + PoP + "%，記得要攜帶雨具出門喔！");
        }catch (NullPointerException e){
            hinttext.setText("抓不到資料我們深感抱歉QQ");
        }

        myViewPager = findViewById( R.id.myViewPager);
        tabLayout = findViewById( R.id.TabLayout);
        setViewPager();
        tabLayout.setupWithViewPager(myViewPager);
        setTabLayoutIcon();
    }
    public void setTabLayoutIcon(){
        //新增tab&icon
        for(int i =0; i < IconResID.length;i++){
            tabLayout.getTabAt(i).setIcon(IconResID[i]);
        }
    }
    private void setViewPager(){
        //新增各Fragment到ViewPager----需同步更動PagerAdapter.java
        clothes1Fragment myFragment1 = new clothes1Fragment();
        clothes2Fragment myFragment2 = new clothes2Fragment();
        clothes3Fragment myFragment3 = new clothes3Fragment();
        clothes4Fragment myFragment4 = new clothes4Fragment();
        clothes5Fragment myFragment5 = new clothes5Fragment();
        clothes6Fragment myFragment6 = new clothes6Fragment();
        clothes7Fragment myFragment7 = new clothes7Fragment();
        clothes8Fragment myFragment8 = new clothes8Fragment();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentList.add(myFragment3);
        fragmentList.add(myFragment4);
        fragmentList.add(myFragment5);
        fragmentList.add(myFragment6);
        fragmentList.add(myFragment7);
        fragmentList.add(myFragment8);
        PagerAdapter myFragmentAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
    }

    public static void getpicture(long id,Context ctxt){
        getClothesMember member = dao.getoneID(id);
        Bitmap bmp= BitmapFactory.decodeFile( member.getImgPath());
        CustomBitmap customBitmap = new CustomBitmap(bmp);
        customBitmap.setId(id);
        if (getSavedMatrix(id,ctxt) != null){
            customBitmap.setMatrix(getSavedMatrix(id,ctxt));
        }
        mView.addBitmap(customBitmap);
        mView.refresh();
    }
    private void saveMatrix(CustomBitmap customBitmap){
        SharedPreferences.Editor editor = getSharedPreferences("matrix",Context.MODE_PRIVATE).edit();
        Matrix matrix = customBitmap.matrix;
        float[] values = new float[9];
        matrix.getValues(values);
        JSONArray array = new JSONArray();
        for (float value:values){
            try {
                array.put(value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(String.valueOf(customBitmap.getId()), array.toString());
        editor.commit();
    }

    //獲取matrix
    private static Matrix getSavedMatrix(long id ,Context ctxt){
        SharedPreferences sp = ctxt.getSharedPreferences("matrix", Context.MODE_PRIVATE);
        String result = sp.getString(String.valueOf(id), null);
        if (result != null){
            float[] values = new float[9];
            Matrix matrix = new Matrix();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    values[i] = Float.valueOf(String.valueOf(array.getDouble(i)));

                }
                matrix.setValues(values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return matrix ;
        }
        return null;
    }

    public void save(View view){
        List<CustomBitmap> list = mView.getViews();
        for (CustomBitmap customBitmap:list){
            saveMatrix(customBitmap);
        }
        wearSet = new HashSet<String>();
        WEAR.edit().clear();
        WEAR.edit().remove("wearSet");
        for(int i=0 ;i < wearlist.size() ; i++){
            wearSet.add(wearlist.get(i).toString());
        }
        Set<String> result = new HashSet<>(wearSet);
        WEAR.edit().putStringSet("wearSet",result).commit();
    }

    public void deletepic(View view){
        try {
            mView.delectpic();
        }catch (NullPointerException e){
            Toast.makeText(this, "請先點選一件衣服", Toast.LENGTH_LONG).show();
        }
    }
    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( viewclothes.this  , MainActivity.class);
        startActivity(intent);
        viewclothes.this.finish();
    }
    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, viewclothes.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }
    @Override
    public void finish() {
        dao.close();
        weatherDAO.close();
        save(getCurrentFocus());
        super.finish();
    }
    //禁用原生返回鍵
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

}
