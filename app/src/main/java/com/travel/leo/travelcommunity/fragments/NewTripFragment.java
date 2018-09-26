package com.travel.leo.travelcommunity.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.travel.leo.travelcommunity.R;
import com.travel.leo.travelcommunity.mapsactivity.MapActivity;
import com.travel.leo.travelcommunity.model.TourModel;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewTripFragment extends Fragment {

    private static final String TAG = "NewTripFragment";
    private static final int ERROR_DIALOG_REQUEST = 1;

    Button cancelBtn,saveBtn;
    private EditText tourTitle,tourDesc,tourStartPlace,tourEndPlace,tourStartDate,tourEndDate;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference uRef;
    StorageReference storageRef;

    String tourCount="0";



    public NewTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: called ");
        View nTripView=  inflater.inflate(R.layout.fragment_new_trip, container, false);
        
        init(nTripView);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Back to trip Fragments ", Toast.LENGTH_SHORT).show();
               backTofragment();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save new tripPlan to database
                startPosting();
                backTofragment();

            }
        });




        tourStartPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isServicesOK()){

                    gotoMap();

                }

            }
        });

        tourEndPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isServicesOK()){

                    gotoMap();

                }

            }
        });


        
        return nTripView;
    }

    public boolean isServicesOK()
    {
        Log.d(TAG,"isServicesOk: checking google services version");

        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if(available== ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"isServicesOk: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG,"isServicesOk: error occured we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(getActivity().getParent(),available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.d(TAG,"we can't make map request");
        }
        return false;
    }

    private void gotoMap()
    {
        Intent goMap=new Intent(getActivity(),MapActivity.class);
        startActivity(goMap);
    }

    private void backTofragment() {
        Fragment frag= new TripsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contain_fram,
                frag).commit();
        try {
            NewTripFragment.this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void startPosting() {
        Log.i(TAG, "startPosting: ");

        String titleString=tourTitle.getText().toString();
        String descString= tourDesc.getText().toString();
        String startPlaceString= tourStartPlace.getText().toString();
        String endPlaceString= tourEndPlace.getText().toString();

        String startDateString=tourStartDate.getText().toString();
        String endDateString=tourEndDate.getText().toString();


        if(!TextUtils.isEmpty(titleString)&&
                !TextUtils.isEmpty(descString) &&
                !TextUtils.isEmpty(startDateString) &&
                !TextUtils.isEmpty(endDateString) &&
                !TextUtils.isEmpty(startPlaceString) &&
                !TextUtils.isEmpty(endPlaceString))
        {
            Log.i(TAG, "startPosting: nothing empty");
            //start uploading
            TourModel demoTour=new TourModel(titleString,descString,startPlaceString,endPlaceString,startDateString,endDateString);
            //set tour info to demoTour
            Random r=new Random();
            int rand=r.nextInt(1000);
            demoTour.setTourId(String.valueOf(rand));
            demoTour.setUserId(mUser.getUid());
            demoTour.setWalkingDistance("10");
            demoTour.setCoverDistance("50");
            demoTour.setRemDistance("100");
            demoTour.setOnlineStatus("true");
            demoTour.setLikeStatus("true");
            demoTour.setMapImage("https://firebasestorage.googleapis.com/v0/b/travelcommunity-51b99.appspot.com/o/userprofile%2Fimage%3A393825?alt=media&token=285cbfe1-1cac-4812-b45d-6f37504eccc1");


           DatabaseReference dataRef=database.getReference("Users");

           dataRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  tourCount=dataSnapshot.child(mUser.getUid()).child("totaltour").getValue(String.class);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });





            int n=(Integer.valueOf(tourCount)+1);

            tourCount=String.valueOf(n);
            Log.i(TAG, "startPosting: total tour: "+tourCount);

            dataRef.child(mUser.getUid()).child("totaltour").setValue(tourCount);


            ////important
            uRef.child(demoTour.getTourId()).setValue(demoTour);


        }
        else {
            Log.i(TAG, "startPosting: some fields are empty");
        }


    }

    private void init(View newTripFragmentView) {

        cancelBtn=newTripFragmentView.findViewById(R.id.cancel_btn);
        saveBtn= newTripFragmentView.findViewById(R.id.save_btn);

        tourTitle= newTripFragmentView.findViewById(R.id.tour_title_id);
        tourDesc= newTripFragmentView.findViewById(R.id.tour_desc_id);
        tourStartDate= newTripFragmentView.findViewById(R.id.tour_start_date__input_et);
        tourEndDate= newTripFragmentView.findViewById(R.id.tour_end_date_input_et);
        tourStartPlace= newTripFragmentView.findViewById(R.id.tour_to_startplace_input_et);
        tourEndPlace=newTripFragmentView.findViewById(R.id.tour_to_endplace_input_et);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        uRef=database.getReference().child("Trips");

    }

}
