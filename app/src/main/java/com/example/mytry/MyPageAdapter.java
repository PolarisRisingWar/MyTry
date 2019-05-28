package com.example.mytry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {

    private String[] title=new String[]{"First","Second","third"};
    public MyPageAdapter(FragmentManager manager){
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new FirstFragment();
        }else if (position==1){
            return new SecondFragment();
        }else {
            return new ThirdFragment();
        }
    }

    public CharSequence getPageTitle(int position){
        return title[position];
    }

    @Override
    public int getCount() {//返回需要管理的fragment有多少个
        return 3;
    }
}
