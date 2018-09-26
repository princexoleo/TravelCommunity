package com.travel.leo.travelcommunity.mapsactivity;

import android.Manifest;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class BackgroundTrackingServics extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = BackgroundTrackingServics.class.getSimpleName();
    private static final long INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    //Firebase database
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference dataRef;


    String latitude;
    String longitude;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        super.onStartCommand(intent, flags, startId);
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        database.goOffline();

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

        mGoogleApiClient.disconnect();
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: called ");
        super.onCreate();

        mContext = this;

        database=FirebaseDatabase.getInstance();
        dataRef=database.getReference("Users");

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        dataRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latitude=dataSnapshot.child("latitude").getValue(String.class);
                longitude=dataSnapshot.child("longitude").getValue(String.class);

                Log.i(TAG, "onDataChange: Latitude: "+latitude+" and" +
                        "Longitude: "+longitude);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d(TAG, "Latitude" + location.getLatitude() + " Longitude:"
                    + location.getLongitude());
            dataRef.child(mUser.getUid()).child("latitude").setValue(String.valueOf(location.getLatitude()));
            dataRef.child(mUser.getUid()).child("longitude").setValue(String.valueOf(location.getLongitude()));
            Toast.makeText(mContext, "Location Updated", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}