package com.travel.leo.travelcommunity.viewholder;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.travel.leo.travelcommunity.Interface.ItemClickListener;
import com.travel.leo.travelcommunity.R;

public class PostViewHolder extends RecyclerView.ViewHolder{

    //SupportMapFragment mapFragment;



    View mView;
    public MapView mapView;
    public TextView user_name;
    public TextView user_description;
    public ImageView user_proImage;
    public ImageView user_mapImage;
   //public GoogleMap userMap;

   //public Location location;
//    Fragment mapFragment;


    private ItemClickListener itemClickListener;


    public PostViewHolder(View itemView) {
        super(itemView);

        mView=itemView;

        user_name=mView.findViewById(R.id.person_name_tv);
        user_description=mView.findViewById(R.id.person_post_desc_et);
        user_proImage=mView.findViewById(R.id.person_profile_imageView);
        user_mapImage=mView.findViewById(R.id.person_map_imageView);
       // userMap= itemView.findViewById(R.id.map_id);
        //mapView=mView.findViewById(R.id.map_view_user);

        //mapView.getMapAsync((OnMapReadyCallback) this);







    }
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        MarkerOptions markersource = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Me").snippet("This is my current location");
//        userMap.addMarker(markersource);
//        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16);
//         userMap.animateCamera(cu);
//    }
}
