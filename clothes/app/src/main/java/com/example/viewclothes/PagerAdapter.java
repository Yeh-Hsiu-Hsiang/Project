package com.example.viewclothes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.viewclothes.fragment.clothes1Fragment;
import com.example.viewclothes.fragment.clothes2Fragment;
import com.example.viewclothes.fragment.clothes3Fragment;
import com.example.viewclothes.fragment.clothes4Fragment;
import com.example.viewclothes.fragment.clothes5Fragment;
import com.example.viewclothes.fragment.clothes6Fragment;
import com.example.viewclothes.fragment.clothes7Fragment;
import com.example.viewclothes.fragment.clothes8Fragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                clothes1Fragment tab1=new clothes1Fragment();
                return tab1;
            case 1:
                clothes2Fragment tab2=new clothes2Fragment();
                return tab2;
            case 2:
                clothes3Fragment tab3=new clothes3Fragment();
                return tab3;
            case 3:
                clothes4Fragment tab4=new clothes4Fragment();
                return tab4;
            case 4:
                clothes5Fragment tab5=new clothes5Fragment();
                return tab5;
            case 5:
                clothes6Fragment tab6=new clothes6Fragment();
                return tab6;
            case 6:
                clothes7Fragment tab7=new clothes7Fragment();
                return tab7;
            case 7:
                clothes8Fragment tab8=new clothes8Fragment();
                return tab8;
        }
        return null;
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

