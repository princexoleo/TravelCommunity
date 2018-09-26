package com.travel.leo.travelcommunity.activity;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.travel.leo.travelcommunity.MainActivityLogin;
import com.travel.leo.travelcommunity.R;
import com.travel.leo.travelcommunity.fragments.FeedFragment;
import com.travel.leo.travelcommunity.fragments.NotificationFragment;
import com.travel.leo.travelcommunity.fragments.ProfileFragment;
import com.travel.leo.travelcommunity.fragments.SearchFragment;

import android.view.View;
import android.widget.Toast;

import com.travel.leo.travelcommunity.utils.BottomNavigationViewHelper;

import java.io.FileNotFoundException;
import java.io.IOException;


public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "HomeActivity";
    private static final int PICK_IMAGE_REQUEST = 71;
    BottomNavigationViewEx bottomNavigationViewEx;
    private DrawerLayout mDrawerLayout;
    SharedPreferences preferences;
    private SharedPreferences loginPreferences;
    ProgressDialog mDialog;

    TextView userNameTextView;
    TextView userLocationTextView;
    ImageView profileImageView;

    DatabaseReference propicRef;

    private Uri mImageUri;


    //Firebase storage refrence
    FirebaseDatabase database;
    DatabaseReference dataref;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageTask mUploadTask;


    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        loginPreferences = getBaseContext().getSharedPreferences("loginDetails", MODE_PRIVATE);

        NavigationView navigationView = findViewById(R.id.nav_view);

        //this is for user header view in drawer layout
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.user_name_id);
        userLocationTextView = headerView.findViewById(R.id.user_location_id);
        profileImageView = headerView.findViewById(R.id.user_pro_image);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: prepared for upload");
                choseImage();
                //uploadImage();
            }
        });

       dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child(mUser.getUid()).child("name").getValue(String.class);
                String city=dataSnapshot.child(mUser.getUid()).child("city").getValue(String.class);
               String imageUri=dataSnapshot.child(mUser.getUid()).child("profileImage").getValue(String.class);
                Log.i(TAG, "onDataChange: name: "+name+" city: "+city);

                userNameTextView.setText(name);
                userLocationTextView.setText(city);
              if (imageUri!=null){
                  Glide.with(HomeActivity.this).load(imageUri).into(profileImageView);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId() == R.id.nav_logout) {
                    preferences = getBaseContext().getSharedPreferences("userinfo", MODE_PRIVATE);
                    preferences.edit().clear().apply();
                    Log.i("HomeActivity::", "logoutBtn: logout succesfully done");
                    mAuth.signOut();
                    Intent getBack = new Intent(HomeActivity.this, MainActivityLogin.class);
                    startActivity(getBack);
                    finish();

                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });


        Fragment profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                profileFragment).commit();
        //Bottom navigation view
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(navListener);

    }


    //chose image from gallery
    private void choseImage() {
        Log.i(TAG, "choseImage: image select");

        Intent choseIntent = new Intent();
        choseIntent.setType("image/*");
        choseIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(choseIntent, "Select Picture"), PICK_IMAGE_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Log.i(TAG, "onActivityResult: result");
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(profileImageView);
            try {
                mDialog=new ProgressDialog(this);
                mDialog.setMessage("Uploading....");
                mDialog.show();
                //upload firebase
               // StorageReference path= storageReference.child("ProfilePhotos").child(preferences.getString("userphone","")).child(filePath.getLastPathSegment());
                //mImage.getLastpathSegment= /image/myphoto.jpg"
                final StorageReference filepath=storageReference.child("userprofile").child(mImageUri.getLastPathSegment());
//              dataref.child(mUser.getUid()).child("profileImage").setValue(mImageUri);



                filepath.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                mDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Upload successfully", Toast.LENGTH_SHORT).show();

                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dataref.child(mUser.getUid()).child("profileImage").setValue(uri.toString());
                                    }
                                });
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mDialog.dismiss();
                        Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        mDialog.setMessage("Uploaded "+progress);
                    }
                });


                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

          }

        }

//    private String getImageExt(Uri mImageUri) {
//        ContentResolver contentResolver=getContentResolver();
//        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(mImageUri));
//    }

    //upload image to firebase database
        private void initSetProfile(Uri uri){


            Log.i(TAG, "initSetProfile: done: uri= "+uri);

//          if (!uri.equals(null))
//          {
//              Glide.with(getApplicationContext()).load(uri).into(profileImageView);
//
//          }
        }


        private void init() {
            setupNavigationView(); //botoom navigation
            mDrawerLayout = findViewById(R.id.drawerLayout);
            preferences = getSharedPreferences("userinfo", MODE_PRIVATE);


            //
            database = FirebaseDatabase.getInstance();
            mAuth=FirebaseAuth.getInstance();
            mUser=mAuth.getCurrentUser();
            dataref=database.getReference("Users");

            storage=FirebaseStorage.getInstance();
            storageReference=storage.getReference();



        }

    /**
     * Bottom Navigation Setup
     */
    private void setupNavigationView()
    {
        Log.i(TAG,"setup bottomNavigation View");
        bottomNavigationViewEx=findViewById(R.id.bottom_navigationBar_id);
        BottomNavigationViewHelper.setupBottomNavView(bottomNavigationViewEx);
    }
    private BottomNavigationViewEx.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;


                    switch (item.getItemId())
                    {
                        case R.id.menu_home:
                            selectedFragment=new ProfileFragment();

                            break;
                        case R.id.menu_noti:
                            selectedFragment=new NotificationFragment();
                            break;
                        case R.id.menu_search:
                            selectedFragment=new SearchFragment();

                            break;
//                        case R.id.menu_camera:
//                        selectedFragment= new CameraFragment();
//                        break;
                        case R.id.menu_feed:
                            selectedFragment= new FeedFragment();

                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

}

