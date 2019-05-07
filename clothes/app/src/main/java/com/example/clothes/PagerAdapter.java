package com.example.clothes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

