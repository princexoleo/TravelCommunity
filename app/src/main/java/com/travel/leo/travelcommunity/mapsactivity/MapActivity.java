package com.travel.leo.travelcommunity.mapsactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.travel.leo.travelcommunity.R;
import com.travel.leo.travelcommunity.model.PlaceInfo;

public class MapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUESTED_CODE = 1234;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136)
    );
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;


    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 14f;

    // private AutocompletePrediction mSearchText;
    private ImageView mGps;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    protected GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private PlaceInfo mPlace;

    public static Location currentLocation;

    FirebaseDatabase database;
    DatabaseReference dataRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //mSearchText=findViewById(R.id.search_view_id);
        mGps = findViewById(R.id.gps_imageView);
        database=FirebaseDatabase.getInstance();
        dataRef = database.getReference("Users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        getLocationPermission();
        init();



    }

    private void init() {

        mGeoDataClient = Places.getGeoDataClient(getApplicationContext(),null);

        mGoogleApiClient= (GoogleApiClient)new GoogleApiClient
                .Builder(MapActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(MapActivity.this,this)
                .build();


        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked GPS icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }

    private void getLocationPermission() {
        Log.d(TAG, "getting Location Permission");
        String[] permission = {FINE_LOCATION,
                COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //set Boolean;
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permission,
                        LOCATION_PERMISSION_REQUESTED_CODE
                );
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permission,
                    LOCATION_PERMISSION_REQUESTED_CODE
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUESTED_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;

                        }
                    }
                    mLocationPermissionGranted = true;
                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    //init map
                    initMap();

                }
            }

        }
    }

    private void initMap() {
        Log.i(TAG, "initMap: initialize map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                try{
                    boolean success=mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getApplicationContext(),R.raw.mapstyle
                            )
                    );
                    
                    if (!success)
                    {
                        Log.d(TAG, "onMapReady:  Style parsing failed");
                    }
                }catch (Resources.NotFoundException e)
                {
                    Log.d(TAG, "onMapReady:can't find Style Error");
                }




                Toast.makeText(MapActivity.this, "map is ready", Toast.LENGTH_SHORT).show();

                if (mLocationPermissionGranted) {
                    getDeviceLocation();
                    if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            }
        });

    }

    private void getDeviceLocation() {
        Log.i(TAG, "getDeviceLocation: getting the device current location ");

        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(MapActivity.this);

        try {
            if(mLocationPermissionGranted)
            {
                Task location =mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: FoundLocation ");
                            currentLocation= (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,
                           "My Location") ;

                            //save to database
                            dataRef.child(mUser.getUid()).child("latitude").setValue(currentLocation.getLatitude());
                            dataRef.child(mUser.getUid()).child("longitude").setValue(currentLocation.getLongitude());
                            Log.d(TAG, "onComplete: update to database ");


                        }

                    }
                });
            }
        }
        catch (SecurityException e){
            Log.d(TAG, "getDeviceLocation: "+e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float defaultZoom, String my_location) {
        Log.d(TAG, "moveCamera: moving camera to 1st: "+latLng+", lng: "+latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,defaultZoom));

        if (!my_location.equals("My Location"))
        {
            MarkerOptions markerOptions=new MarkerOptions()
                    .position(latLng)
                    .title(my_location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_24dp))
                    ;
            mMap.addMarker(markerOptions);

        }
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

