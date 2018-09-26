package com.travel.leo.travelcommunity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.fabtransitionactivity.SheetLayout;
import com.travel.leo.travelcommunity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends Fragment implements SheetLayout.OnFabAnimationEndListener {
    public static final String TAG="StatisticsFragments: ";

    SheetLayout mSheetLayout;
    FloatingActionButton mFab;
    Button mButton;

    private static final int REQUEST_CODE = 1;


    public TripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"statistics fragments working ");
        View fragmentView=inflater.inflate(R.layout.fragment_trips, container, false);

        initialize(fragmentView);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetLayout.expandFab();
                mButton.setVisibility(View.INVISIBLE);

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent gotrack= new Intent(getActivity(), BackgroundTrackingServics.class);
//                startActivity(gotrack);

                //start track and update to database
                //new BackgroundTrackingServics();
            }
        });

        return  fragmentView;
    }

    private void initialize(View fragmentView) {
        mSheetLayout =fragmentView.findViewById(R.id.bottom_sheet);
        mFab  = fragmentView.findViewById(R.id.fab);


        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);

        mButton=fragmentView.findViewById(R.id.location_track_button);
        mButton.setVisibility(View.VISIBLE);

    }




    @Override
    public void onFabAnimationEnd() {
        //open add trip layout
        //after click fab button
        mSheetLayout.hide();
        Fragment newTripsFragment= new NewTripFragment();
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contain_fram,
               newTripsFragment).commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE)
        {
            mSheetLayout.contractFab();
        }
    }
}
