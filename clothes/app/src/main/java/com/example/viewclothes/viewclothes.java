package com.example.viewclothes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

import java.util.ArrayList;
import java.util.List;


public class viewclothes extends AppCompatActivity {

    // 宣告資料庫功能類別欄位變數
    private clothesDAO dao;

    private ViewGroup.LayoutParams mVgLp;

    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one, R.drawable.selector_two, R.drawable.selector_three,
            R.drawable.selector_four, R.drawable.selector_five, R.drawable.selector_six,
            R.drawable.selector_seven, R.drawable.selector_eight};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclothes);

        dao = new clothesDAO(getApplicationContext());
        //測試用
        getpicture(21);
        getpicture(23);
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

    public void getpicture(long id){
        getClothesMember member = dao.getoneID(id);
        TouchImageView imageView = new TouchImageView(getApplicationContext());
        Bitmap bitmap = BitmapFactory.decodeFile( member.getImgPath());
        imageView.setImageBitmap(bitmap);

        RelativeLayout parentLayout = (RelativeLayout)findViewById(R.id.parentrelativeLayout);
        mVgLp = parentLayout.getLayoutParams();

        FrameLayout frame = (FrameLayout)findViewById(R.id.frameLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVgLp);

        frame.addView(imageView,params);

    }
}
