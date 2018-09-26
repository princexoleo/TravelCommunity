package com.travel.leo.travelcommunity.utils;

import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper  {
    private static final String TAG= "BottomNavigationViewHe";


    public static void setupBottomNavView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG,"setupBottomNavigation: Setting up.....");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
}
