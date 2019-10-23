package com.example.clothes;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.clothes.fragment.clothes1Fragment;
import com.example.clothes.fragment.clothes2Fragment;
import com.example.clothes.fragment.clothes3Fragment;
import com.example.clothes.fragment.clothes4Fragment;
import com.example.clothes.fragment.clothes5Fragment;
import com.example.clothes.fragment.clothes6Fragment;
import com.example.clothes.fragment.clothes7Fragment;
import com.example.clothes.fragment.clothes8Fragment;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Manageclothes extends AppCompatActivity {

    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one, R.drawable.selector_two, R.drawable.selector_three,
            R.drawable.selector_four, R.drawable.selector_five, R.drawable.selector_six,
            R.drawable.selector_seven, R.drawable.selector_eight};
    //在別的activity中關閉自己的方法
    public static Manageclothes finishself = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_manageclothes);
        finishself = this;

        //去新增衣服
        ImageButton addclothes = (ImageButton)findViewById( R.id.Addclothes);
        addclothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.setClass( Manageclothes.this  , newclothes.class);
                startActivity(intent);

            }
        });

        processView();
    }
    public void processView(){
        myViewPager = (ViewPager) findViewById( R.id.myViewPager);
        tabLayout = (TabLayout) findViewById( R.id.TabLayout);
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
        List<Fragment> fragmentList = new ArrayList<Fragment> ();
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

    // 回到主頁按鈕
    public void toHome(View view) {
        Intent intent = new Intent();
        intent.setClass( Manageclothes.this  , MainActivity.class);
        startActivity(intent);
        Manageclothes.this.finish();
    }

    // 重新整理按鈕
    public void reLoad(View view) {
        Intent intent=new Intent(this, Manageclothes.class);
        startActivity(intent);
        finish(); // 關閉此檔案
        overridePendingTransition(0, 0);
    }
}

