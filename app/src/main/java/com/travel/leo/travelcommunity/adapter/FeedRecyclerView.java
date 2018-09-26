package com.travel.leo.travelcommunity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travel.leo.travelcommunity.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedRecyclerView extends RecyclerView.Adapter<FeedRecyclerView.ViewHolder>{
    private static final String TAG = "FeedRecyclerView";

   ArrayList<String> personName =new ArrayList<>();
   ArrayList<String> personProfileImage= new ArrayList<>();
   ArrayList<String> personOnlineStatus=new ArrayList<>();
   ArrayList<String> personWalkingDistance= new ArrayList<>();
   ArrayList<String> personCoverDistance =new ArrayList<>();
   ArrayList<String> personReminingDistance=new ArrayList<>();
   ArrayList<String> personLikeStatus =new ArrayList<>();
   ArrayList<String> personMapsImage =new ArrayList<>();

   Context mContext;

    public FeedRecyclerView(ArrayList<String> personName, ArrayList<String> personProfileImage,
                            ArrayList<String> personOnlineStatus, ArrayList<String> personWalkingDistance,
                            ArrayList<String> personCoverDistance, ArrayList<String> personReminingDistance,
                            ArrayList<String> personLikeStatus, ArrayList<String> personMapsImage, Context mContext) {
        this.personName = personName;
        this.personProfileImage = personProfileImage;
        this.personOnlineStatus = personOnlineStatus;
        this.personWalkingDistance = personWalkingDistance;
        this.personCoverDistance = personCoverDistance;
        this.personReminingDistance = personReminingDistance;
        this.personLikeStatus = personLikeStatus;
        this.personMapsImage = personMapsImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View recylerView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_feed_list,
                parent,false);

        ViewHolder mHolder= new ViewHolder(recylerView);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(personProfileImage.get(position))
                .into(holder.pProfileImg);

        Glide.with(mContext)
                .asBitmap()
                .load(personMapsImage.get(position))
                .into(holder.pTourMapsImg);

        holder.pWalkingTV.setText("Walking: "+personWalkingDistance.get(position)+" km");
        holder.pDistanceRemTV.setText("remDistance: "+personReminingDistance.get(position)+" km");
        holder.pDistanceCoverTV.setText("coverDistance: "+personCoverDistance.get(position)+" km");

        holder.pNameTV.setText(personName.get(position));
        if(personLikeStatus.get(position).equals("true"))
        {
            holder.pLikeBtn.setVisibility(View.VISIBLE);
            holder.pNoLike.setVisibility(View.INVISIBLE);
        }
        else {
            holder.pLikeBtn.setVisibility(View.INVISIBLE);
            holder.pNoLike.setVisibility(View.VISIBLE);
        }
        if(personOnlineStatus.get(position).equals("true"))
        {
            holder.pOnlinleStatusTV.setText("Active now");
            holder.pOnlineView.setVisibility(View.VISIBLE);
            holder.pOfflineView.setVisibility(View.INVISIBLE);
        }
        else {
            holder.pOnlinleStatusTV.setText("3 hours ago");
            holder.pOnlineView.setVisibility(View.INVISIBLE);
            holder.pOfflineView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+personName.size());
        return personName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton pShareBtn;
        ImageView pLikeBtn, pNoLike,pOnlineView,pOfflineView;
        CircleImageView pProfileImg;
        ImageView pTourMapsImg;

        EditText pPostEt;

        TextView pNameTV,pOnlinleStatusTV;
        TextView pWalkingTV, pDistanceCoverTV,pDistanceRemTV;


        public ViewHolder(View itemView) {
            super(itemView);

            pProfileImg = itemView.findViewById(R.id.person_profile_imageView);
            pLikeBtn = itemView.findViewById(R.id.person_like_heart_red);
            pNoLike = itemView.findViewById(R.id.person_like_heart_white);
            //pOnlineView = itemView.findViewById(R.id.person_online_view);
            pOfflineView = itemView.findViewById(R.id.person_offline_view);
           // pTourMapsImg = itemView.findViewById(R.id.person_map_imageView);
            pPostEt = itemView.findViewById(R.id.person_post_desc_et);
            pNameTV = itemView.findViewById(R.id.person_name_tv);
            pOnlinleStatusTV = itemView.findViewById(R.id.person_onlinestatus_tv);
            pWalkingTV = itemView.findViewById(R.id.person_tour_walking_tv);
            pDistanceCoverTV = itemView.findViewById(R.id.person_tour_distance_cover_tv);
            pDistanceRemTV = itemView.findViewById(R.id.person_tour_distance_remining_tv);


        }


    }
}
