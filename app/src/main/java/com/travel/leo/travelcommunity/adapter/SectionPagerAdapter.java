package com.travel.leo.travelcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter  extends FragmentPagerAdapter {
    public static final String TAG="SectionPagerAdapter: ";
    private static List<Fragment>mFragments= new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fragmentManager) {
       super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }
    public void addFragments(Fragment fragment)
    {
        mFragments.add(fragment);
    }
}
