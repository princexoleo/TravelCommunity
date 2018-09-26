package com.travel.leo.travelcommunity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.travel.leo.travelcommunity.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitedLocationRecyclerViewAdapter extends RecyclerView.Adapter<VisitedLocationRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "VisitedLocationRecycler";
    private ArrayList<String> vLocationNames = new ArrayList<>();
    private ArrayList<String> vLocationImageName = new ArrayList<>();
    private Context mContext;

    public VisitedLocationRecyclerViewAdapter(ArrayList<String> vLocationNames, ArrayList<String> vLocationImageName, Context mContext) {
        this.vLocationNames = vLocationNames;
        this.vLocationImageName = vLocationImageName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHoler called..");

        View recyleView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location_vistied_list,
                parent,false);
        ViewHolder mHolder= new ViewHolder(recyleView); //this is hold our view

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG,"onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(vLocationImageName.get(position))
                .into(holder.vlocationImageview);

        holder.vlocationnametv.setText(vLocationNames.get(position));

    }

    @Override
    public int getItemCount() {
        Log.i(TAG,"imageNameCount: "+vLocationImageName.size());
        return vLocationImageName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView vlocationImageview;
        TextView vlocationnametv;

        public ViewHolder(View itemView) {
            super(itemView);

            vlocationImageview = itemView.findViewById(R.id.visited_location_imageView);

            vlocationnametv = itemView.findViewById(R.id.visited_location_name_tv);

        }
    }
}
