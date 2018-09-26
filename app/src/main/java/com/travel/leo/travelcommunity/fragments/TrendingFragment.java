package com.travel.leo.travelcommunity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.leo.travelcommunity.R;

public class TrendingFragment extends Fragment {

    public static final String TAG="TrendingFragments:";




    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"Trending fragments working ");
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }
}
