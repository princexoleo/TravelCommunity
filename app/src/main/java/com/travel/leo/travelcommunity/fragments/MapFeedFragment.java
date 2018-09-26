package com.travel.leo.travelcommunity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.travel.leo.travelcommunity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFeedFragment extends Fragment {

    GoogleMap userGoogleMap;


    public MapFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v=inflater.inflate(R.layout.fragment_map_feed, container, false);
        // Inflate the layout for this fragment
        return v;


    }

}
