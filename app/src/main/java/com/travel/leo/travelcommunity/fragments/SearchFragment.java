package com.travel.leo.travelcommunity.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.leo.travelcommunity.R;
import com.travel.leo.travelcommunity.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String TAG="SearchFragments: ";

    private ArrayList<String> mFriendsNames= new ArrayList<>();
    private ArrayList<String> mFriendsImageUriList = new ArrayList<>();
    private ArrayList<String> mFriendsLocationArrayList = new ArrayList<>();






    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,"Search fragments working ");
        View searchView=inflater.inflate(R.layout.fragment_search, container, false);
        init(searchView);
        initImageBitmaps(searchView);
        return searchView;
    }

    private void init(View mView) {

    }
    private void initImageBitmaps(View mView){
        Log.i(TAG,"initImageBitmaps: preparing bitmaps ");

        mFriendsImageUriList.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mFriendsNames.add("Mazharul Islam Leon");
        mFriendsLocationArrayList.add("Mymensingh , Bangladesh");

        mFriendsImageUriList.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mFriendsNames.add("Trondheim");
        mFriendsLocationArrayList.add("Dhaka , Bangladesh");

        mFriendsImageUriList.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mFriendsNames.add("Portugal");
        mFriendsLocationArrayList.add("Fraidpur , Bangladesh");

        mFriendsImageUriList.add("https://i.redd.it/j6myfqglup501.jpg");
        mFriendsNames.add("Rocky Mountain National Park");
        mFriendsLocationArrayList.add("Comilla , Bangladesh");


        mFriendsImageUriList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mFriendsNames.add("Mahahual");
        mFriendsLocationArrayList.add("Mymensingh , Bangladesh");

        mFriendsImageUriList.add("https://i.redd.it/k98uzl68eh501.jpg");
        mFriendsNames.add("Frozen Lake");
        mFriendsLocationArrayList.add("Rangpur , Bangladesh");


        mFriendsImageUriList.add("https://i.redd.it/glin0nwndo501.jpg");
        mFriendsNames.add("White Sands Desert");
        mFriendsLocationArrayList.add("Khulna , Bangladesh");

        mFriendsImageUriList.add("https://i.redd.it/obx4zydshg601.jpg");
        mFriendsNames.add("Austrailia");
        mFriendsLocationArrayList.add("Sylhet , Bangladesh");

        mFriendsImageUriList.add("https://i.imgur.com/ZcLLrkY.jpg");
        mFriendsNames.add("Washington");
        mFriendsLocationArrayList.add("Chittagong , Bangladesh");

        initRecyclerView(mView);


    }

    private void initRecyclerView(View mView) {
        Log.i(TAG, "initRecyclerView: initRecylerView called");

        RecyclerView mRecyclerView= mView.findViewById(R.id.recyclerView_container);

        RecyclerViewAdapter mAdapter= new RecyclerViewAdapter(mFriendsNames,mFriendsImageUriList,mFriendsLocationArrayList,mView.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
