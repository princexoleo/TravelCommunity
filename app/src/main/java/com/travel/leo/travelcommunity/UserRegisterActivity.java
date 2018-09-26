package com.travel.leo.travelcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travel.leo.travelcommunity.model.UserModel;

public class UserRegisterActivity extends AppCompatActivity {
    private static final String TAG = "UserRegisterActivity";
    EditText nNameEditText, nEmailEditText, nPasswordEditText, nPhoneEditText, nCityEditText;
    Button registerButton;
    CheckBox nMaleCB, nFemaleCB;
    ProgressBar mProgressBar;
    boolean cb = false;
    String uName, uEmail, uPassword, uPhone, uCity, uGender;

   // DBHelper mDatabase;
    UserModel temp_userModel = null;

    //Firebase databases
    FirebaseDatabase mDatabase;
    DatabaseReference mDataReference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        init();

        nMaleCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb = true;
                    uGender = "Male";
                    nFemaleCB.setActivated(false);
                    Log.i("nMaleCB:", "Male checkbox checked");
                } else {
                    cb = false;
                    nFemaleCB.setActivated(true);
                }

            }
        });

        nFemaleCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb = true;
                    uGender = "Female";
                    nMaleCB.setActivated(false);
                    Log.i("nFemaleCB:", "female checkbox checked");
                } else {
                    cb = false;
                    nMaleCB.setActivated(true);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uName = nNameEditText.getText().toString();
                uEmail = nEmailEditText.getText().toString();
                uPassword = nPasswordEditText.getText().toString();
                uPhone = nPhoneEditText.getText().toString();
                uCity = nCityEditText.getText().toString();





                Log.i("registerActivity::", "" + nNameEditText.getText().toString() + " " + uPassword);

                Log.i("registerButton", "register Button Clicked");
                if (!TextUtils.isEmpty(uName)&&
                        !TextUtils.isEmpty(uEmail) &&
                        !TextUtils.isEmpty(uPassword) &&
                        !TextUtils.isEmpty(uPhone) &&
                        !TextUtils.isEmpty(uCity) &&
                        cb) {

                    Log.i("registerActivity::", "" + uName + " " + uPassword);
                    Log.i("registerButton", "register Fields are not Empty");
                        Log.i(TAG, "onClick: mAuth=nu;; mUser==null ");

                       createUser();




                } else {
                    Toast.makeText(getBaseContext(), "Filled all the info correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void createUser() {

        mAuth.createUserWithEmailAndPassword(uEmail,uPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: success");
                            final FirebaseUser user=mAuth.getCurrentUser();
                            //create real time database
                            //progress bar
                            mProgressBar=new ProgressBar(UserRegisterActivity.this);
                            //create firebase database
                            mDataReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        mProgressBar.invalidate();
                                        UserModel tempuseUserModel=new UserModel(uName, uEmail, uPassword, uPhone, uGender, uCity);
                                        tempuseUserModel.setTotaltour("0");
                                        tempuseUserModel.setProfileImage(" ");
                                        tempuseUserModel.setOnlineStatus("true");
                                        tempuseUserModel.setLatitude("23");
                                        tempuseUserModel.setLongitude("38");

                                            mDataReference.child(user.getUid()).setValue(tempuseUserModel);
                                            Toast.makeText(UserRegisterActivity.this, "Registerd Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(UserRegisterActivity.this,MainActivityLogin.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }else {
                            Log.d(TAG, "onComplete: failure");
                            Toast.makeText(UserRegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void init() {

        nNameEditText = findViewById(R.id.new_name_et_id);
        nEmailEditText = findViewById(R.id.new_email_et_id);
        nPasswordEditText = findViewById(R.id.new_pass_et_id);
        nPhoneEditText = findViewById(R.id.new_phone_et_id);
        nCityEditText = findViewById(R.id.new_city_et_id);

        nMaleCB = findViewById(R.id.male_checkbox_id);
        nFemaleCB = findViewById(R.id.female_checkbox_id);
        mProgressBar=findViewById(R.id.progressbar);

        registerButton = findViewById(R.id.register_button_id);

      //  mDatabase = new DBHelper(this);
        //initiliaze firebase database
        mDatabase= FirebaseDatabase.getInstance();
        mDataReference=mDatabase.getReference("Users");

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
    }


}
