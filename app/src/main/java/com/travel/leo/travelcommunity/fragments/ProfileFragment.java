package com.travel.leo.travelcommunity.fragments;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.travel.leo.travelcommunity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG="ProfileFragments: ";
   // private CircularImageView uProfile_imageView;
    private Button tripButton,statButton;
    Fragment gotoTripFragment;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"Profile fragments working ");
        View fragview= inflater.inflate(R.layout.fragment_profile, container, false);

        init(fragview);

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"statistics button clicked ");
                Fragment gotoStatFragment= new StatisticsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.in_profile_content_frame,
                        gotoStatFragment).commit();
            }
        });
        tripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"trips button clicked ");
                Fragment gotoTripFragment= new TripsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.in_profile_content_frame,
                        gotoTripFragment).commit();
            }
        });



        return fragview;
    }

    private void init(View fragview) {

//        uProfile_imageView= fragview.findViewById(R.id.user_pro_image);
//
//        uProfile_imageView.setBorderColor(getResources().getColor(R.color.Gray));
//        uProfile_imageView.setBorderWidth(10);
//// Add Shadow with default param
//        uProfile_imageView.addShadow();
//// or with custom param
//        uProfile_imageView.setShadowRadius(20);
//        uProfile_imageView.setShadowColor(Color.RED);
//        uProfile_imageView.setBackgroundColor(Color.RED);
//        uProfile_imageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);

        //button
        tripButton = fragview.findViewById(R.id.tour_button);
        statButton = fragview.findViewById(R.id.stat_button);

        //init framgment container view
        gotoTripFragment= new TripsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.in_profile_content_frame,
                gotoTripFragment).commit();
    }

}
