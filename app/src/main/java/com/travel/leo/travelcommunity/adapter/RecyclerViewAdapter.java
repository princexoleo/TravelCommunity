package com.travel.leo.travelcommunity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travel.leo.travelcommunity.Interface.ItemClickListener;
import com.travel.leo.travelcommunity.R;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    //array list this hold imageView of friends
    private ArrayList<String> mImageName = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mFriendsLocation  =new ArrayList<>();

    private ItemClickListener itemClickListener;

    private Context context;


    public RecyclerViewAdapter(ArrayList<String> mImageName, ArrayList<String> mImage,ArrayList<String>mFriendsLocation, Context context) {
        this.mImageName = mImageName;
        this.mImage = mImage;
        this.mFriendsLocation=mFriendsLocation;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recylerView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_list_item,
                parent,false);
        ViewHolder mHolder= new ViewHolder(recylerView); //this is hold our view

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called");

        Glide.with(context)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.mFriendsImageView);

        holder.mFriendsNameTv.setText(mImageName.get(position));
        holder.mFriendsLocationTv.setText(mFriendsLocation.get(position));

        holder.mFollowRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onBinViewHolder: follow Button Clicked");
                Toast.makeText(context, ""+mImageName.get(position)+" Follow Button clicker", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //how many in List
        return mImageName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mFriendsImageView;
        TextView   mFriendsNameTv;
        TextView   mFriendsLocationTv;
        Button   mFollowRequestButton;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            mFriendsImageView = itemView.findViewById(R.id.suggested_friends_imageView);
            mFriendsNameTv = itemView.findViewById(R.id.suggested_friends_name_textView);
            mFriendsLocationTv =itemView.findViewById(R.id.follers_location_tv);
            mFollowRequestButton = itemView.findViewById(R.id.add_follow_request_button);
            parentLayout= itemView.findViewById(R.id.parent_layout);
        }
    }

}
