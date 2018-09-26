package com.travel.leo.travelcommunity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travel.leo.travelcommunity.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrendingLocationRecyclerViewAdapter extends RecyclerView.Adapter<TrendingLocationRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "TrendingLocationRecycle";

    private ArrayList<String> tLocationNames = new ArrayList<>();
    private ArrayList<String> tLocationImageName = new ArrayList<>();
    private Context mContext;

    public TrendingLocationRecyclerViewAdapter(ArrayList<String> tLocationNames, ArrayList<String> tLocationImageName, Context mContext) {
        this.tLocationNames = tLocationNames;
        this.tLocationImageName = tLocationImageName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: called ");

        View trendingView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trending_location_list,
                parent,false);


        ViewHolder mViewHolder=new ViewHolder(trendingView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.i(TAG,"onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(tLocationImageName.get(position))
                .into(holder.tLocationImageView);

        holder.tLocationNameTv.setText(tLocationNames.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+tLocationImageName.size());
        return tLocationImageName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tLocationNameTv;
        CircleImageView tLocationImageView;


        public ViewHolder(View itemView) {
            super(itemView);

            tLocationImageView = itemView.findViewById(R.id.trending_location_imageView);

            tLocationNameTv = itemView.findViewById(R.id.trending_location_name_tv);
        }
    }
}
