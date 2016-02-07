package com.yogiputra.vynycakeshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.yogiputra.vynycakeshop.fragment.KueBiasa;
import com.yogiputra.vynycakeshop.fragment.KueCustom;


public class MyPagerAdapter extends FragmentPagerAdapter {
    CharSequence title[];
    int NumbOfTabs;
    public MyPagerAdapter(FragmentManager fm, CharSequence mTitle[], int NumOfTabsSum) {
        super(fm);
        this.title=mTitle;
        this.NumbOfTabs=NumOfTabsSum;



    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new KueBiasa();
            case 1:
                return new KueCustom();






        }
        return null;
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
    public CharSequence getPageTitle(int position){
        return title[position];
    }

}
