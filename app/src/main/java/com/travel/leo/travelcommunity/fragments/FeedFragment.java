package com.travel.leo.travelcommunity.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travel.leo.travelcommunity.Interface.ItemClickListener;
import com.travel.leo.travelcommunity.R;
import com.travel.leo.travelcommunity.adapter.FeedRecyclerView;
import com.travel.leo.travelcommunity.adapter.TrendingLocationRecyclerViewAdapter;
import com.travel.leo.travelcommunity.model.TourModel;
import com.travel.leo.travelcommunity.viewholder.PostViewHolder;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    public static final String TAG="FeedFragments: ";

    private ArrayList<String> tLocationImageUri = new ArrayList<>();
    private ArrayList<String> tLocationName = new ArrayList<>();

    //frrdView
    ArrayList<String> personName =new ArrayList<>();
    ArrayList<String> personProfileImage =new ArrayList<>();
    ArrayList<String> personOnlineStatus =new ArrayList<>();
    ArrayList<String> personWalkingDistance=new ArrayList<>();
    ArrayList<String> personCoverDistance =new ArrayList<>();
    ArrayList<String> personReminingDistance=new ArrayList<>();
    ArrayList<String> personLikeStatus=new ArrayList<>();
    ArrayList<String> personMapsImage=new ArrayList<>();
    ArrayList<String> description=new ArrayList<>();

    FirebaseRecyclerAdapter<TourModel,PostViewHolder> adapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    //firebase
    FirebaseDatabase database;
    DatabaseReference dataRef;
    DatabaseReference userRef;

    String name;
    String user_propic;
    String lat;
    String lon;

    GoogleMap mGoogleMap;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i(TAG,"feed fragments working ");
        View feedView=inflater.inflate(R.layout.fragment_feed, container, false);

        mRecyclerView=feedView.findViewById(R.id.mainFeed_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //top trending View initilize
        database=FirebaseDatabase.getInstance();
        dataRef=database.getReference("Trips");
        userRef=database.getReference("Users");


        //load feed
        loadFeed(feedView);

        initTrendingLocationImageBitmap(feedView);
        //initPersonPostViewOnFeed(feedView);
        return feedView;
    }

    private void loadFeed(View feedView) {

        adapter=new FirebaseRecyclerAdapter<TourModel, PostViewHolder>(
                TourModel.class,
                R.layout.layout_main_feed_list,
                PostViewHolder.class,
                dataRef.orderByChild("tourId")
        ) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, TourModel model, int position) {
                String user_id=model.getUserId();

                userRef.child(user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user_propic =dataSnapshot.child("profileImage").getValue(String.class);
                        name=dataSnapshot.child("name").getValue(String.class);

//                       lat=dataSnapshot.child("latitude").getValue(String.class);
//                        lon=dataSnapshot.child("longitude").getValue(String.class);

                        viewHolder.user_name.setText(name);
                        Glide.with(getContext())
                                .load(user_propic)
                                .into(viewHolder.user_proImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Glide.with(getContext())
                        .load(model.getMapImage())
                        .into(viewHolder.user_mapImage);

                viewHolder.user_description.setText(model.getDescription());

//              double l1= Double.parseDouble(lat);
//              double l2=Double.parseDouble(lon);

                //Log.d(TAG, "populateViewHolder: "+l1+"  "+l2);

//
//                viewHolder.location.setLatitude(l1);
//                viewHolder.location.setLatitude(l2);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getContext(), "clicked  "+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);

    }

















//    private void initPersonPostViewOnFeed(View feedView) {
//
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                final String[] name = new String[1];
//                final String[] primg = new String[1];
//                String UserId=dataSnapshot.child("userId").getValue(String.class);
//
//                Log.i(TAG, "onDataChange: userId: "+UserId);
//
//                userRef.child(UserId)
//                        .addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                name[0] =dataSnapshot.child("name").getValue(String.class);
//                                primg[0] =dataSnapshot.child("profileImage").getValue(String.class);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                personName.add(name[0]);
//                personProfileImage.add(primg[0]);
//
//                //tour status
//                personWalkingDistance.add(dataSnapshot.child("walkingDistance").getValue(String.class));
//                personCoverDistance.add(dataSnapshot.child("coverDistance").getValue(String.class));
//                personReminingDistance.add(dataSnapshot.child("remDistance").getValue(String.class));
//                personLikeStatus.add(dataSnapshot.child("likeStatus").getValue(String.class));
//                personMapsImage.add(dataSnapshot.child("mapImage").getValue(String.class));
//                description.add(dataSnapshot.child("description").getValue(String.class));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//        initFeedRecyclerView(feedView);
//
//
//    }

    private void initFeedRecyclerView(View feedView) {
        Log.i(TAG, "initFeedRecyclerView: called ");

        RecyclerView mRecyclerView= feedView.findViewById(R.id.mainFeed_recycler_view);

        FeedRecyclerView fAdapter= new FeedRecyclerView(personName,personProfileImage,
                personOnlineStatus,personWalkingDistance,personCoverDistance,
                personReminingDistance,personLikeStatus,personMapsImage,
                feedView.getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(fAdapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(feedView.getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
    }


    // this is for trending Location
    private void initTrendingLocationImageBitmap(View feedView) {

        Log.i(TAG,"initTrendingLocationImageBitmap: preparing bitmaps ");

        tLocationImageUri.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        tLocationName.add("Mymensingh , Bangladesh");

        tLocationImageUri.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        tLocationName.add("Dhaka , Bangladesh");

        tLocationImageUri.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        tLocationName.add("Fraidpur , Bangladesh");

        tLocationImageUri.add("https://i.redd.it/j6myfqglup501.jpg");
        tLocationName.add("Comilla , Bangladesh");


        tLocationImageUri.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        tLocationName.add("Mymensingh , Bangladesh");

        tLocationImageUri.add("https://i.redd.it/k98uzl68eh501.jpg");
        tLocationName.add("Rangpur , Bangladesh");


        tLocationImageUri.add("https://i.redd.it/glin0nwndo501.jpg");
        tLocationName.add("Khulna , Bangladesh");

        tLocationImageUri.add("https://i.redd.it/obx4zydshg601.jpg");
        tLocationName.add("Sylhet , Bangladesh");

        tLocationImageUri.add("https://i.imgur.com/ZcLLrkY.jpg");
        tLocationName.add("Chittagong , Bangladesh");

        initRecyclerView(feedView);


    }

    private void initRecyclerView(View mView) {
        Log.i(TAG, "initRecyclerView: initRecylerView called");

        RecyclerView mRecyclerView= mView.findViewById(R.id.trending_recyclerView);

        TrendingLocationRecyclerViewAdapter mAdapter = new TrendingLocationRecyclerViewAdapter(tLocationName,tLocationImageUri,mView.getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.HORIZONTAL, true);

        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();
        loadFeed(getView());
    }
}
