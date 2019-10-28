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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothes.R;
import com.example.clothes.database.clothesDAO;
import com.example.clothes.database.getClothesMember;
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

import java.util.ArrayList;
import java.util.List;


public class viewclothes extends AppCompatActivity {

    // 宣告資料庫功能類別欄位變數
    private static clothesDAO dao;

    private static DrawingView mView;

    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one, R.drawable.selector_two, R.drawable.selector_three,
            R.drawable.selector_four, R.drawable.selector_five, R.drawable.selector_six,
            R.drawable.selector_seven, R.drawable.selector_eight};
    private TextView hinttext;

    public static ArrayList wearlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclothes);
        mView = findViewById(R.id.drawingView);
        dao = new clothesDAO(getApplicationContext());

        //測試用
        wearlist.add((long)2);
        wearlist.add((long)1);
        wearlist.add((long)8);
        for(int i=0 ;i < wearlist.size() ; i++){
            getpicture((Long) wearlist.get(i),getApplicationContext());
        }

        processView();
    }

    public void processView(){
        hinttext = findViewById(R.id.hint);
        hinttext.setText("今天降雨機率" + "60%" + "，記得要攜帶雨具出門喔！");
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
            Log.e("tag", "matrix "+ id +" is not null");
            customBitmap.setMatrix(getSavedMatrix(id,ctxt));
        }
        mView.addBitmap(customBitmap);
        mView.refresh();
    }
    private void saveMatrix(CustomBitmap customBitmap){
        Log.e("tag", "save matrix" + customBitmap.getId());
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
        Log.e("tag", "save matrix id:" + customBitmap.getId() + "---------"+
                values[Matrix.MSCALE_X] + " , " + values[Matrix.MSKEW_X] + " , " + values[Matrix.MTRANS_X] + " , " +
                values[Matrix.MSKEW_Y] + " , " +values[Matrix.MSCALE_Y] + " , " + values[Matrix.MTRANS_Y] + " , " +
                values[Matrix.MPERSP_0] + " , " +values[Matrix.MPERSP_1] + " , " + values[Matrix.MPERSP_2]);
    }

    //獲取matrix
    private static Matrix getSavedMatrix(long id ,Context ctxt){
        SharedPreferences sp;
        sp = ctxt.getSharedPreferences("matrix", Context.MODE_PRIVATE);
        String result = sp.getString(String.valueOf(id), null);
        if (result != null){
            float[] values = new float[9];
            Matrix matrix = new Matrix();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    values[i] = Float.valueOf(String.valueOf(array.getDouble(i)));

                }
                //位置重定位
//                 if(values[2]> 250.0 ||  values[5] < -250.0){
//                     Log.e("RE:" ,"RE");
//                     values[2] = (float) 150.0;
//                     values[5] = (float) -150.0;
//                 }
                matrix.setValues(values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("tag", "get matrix id:" + id + "---------"+
                    values[Matrix.MSCALE_X] + " , " + values[Matrix.MSKEW_X] + " , " + values[Matrix.MTRANS_X] + " , " +
                    values[Matrix.MSKEW_Y] + " , " +values[Matrix.MSCALE_Y] + " , " + values[Matrix.MTRANS_Y] + " , " +
                    values[Matrix.MPERSP_0] + " , " +values[Matrix.MPERSP_1] + " , " + values[Matrix.MPERSP_2]);

            return matrix ;
        }
        return null;
    }

    public void save(View view){
        List<CustomBitmap> list = mView.getViews();
        for (CustomBitmap customBitmap:list){
            saveMatrix(customBitmap);
        }
    }
    public void deletepic(View view){
        try {
            mView.delectpic();
        }catch (NullPointerException e){
            Toast.makeText(this, "請先點選一件衣服", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void finish() {
        save(getCurrentFocus());
        if(wearlist != null)
            wearlist = null;
        wearlist = new ArrayList<>();
        super.finish();
    }

}
