package com.example.clothes;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Manageclothes extends AppCompatActivity {

    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one,R.drawable.selector_two,R.drawable.selector_three};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageclothes);

        //去新增衣服
        Button addclothes = (Button)findViewById(R.id.Addclothes);
        addclothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Manageclothes.this  , AddClothes.class);
                startActivity(intent);
            }
        });

        processView();
    }
    public void processView(){
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
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
        //新增各Fragment到ViewPager
        clothes1Fragment myFragment1 = new clothes1Fragment();
        clothes2Fragment myFragment2 = new clothes2Fragment();
        clothes3Fragment myFragment3 = new clothes3Fragment();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentList.add(myFragment3);
        PagerAdapter myFragmentAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
    }
}
