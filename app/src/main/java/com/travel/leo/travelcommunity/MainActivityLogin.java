package com.travel.leo.travelcommunity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.travel.leo.travelcommunity.activity.HomeActivity;
import com.travel.leo.travelcommunity.common.Common;
import com.travel.leo.travelcommunity.database.DBHelper;
import com.travel.leo.travelcommunity.model.UserModel;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class MainActivityLogin extends AppCompatActivity {

    private static final String TAG = "MainActivityLogin";
    Button uLogingButton, nRegisterButton;
    EditText uEmail, uPasswordEditText;
    Boolean cb=false;
    CheckBox rememberCB;
    ArrayList<UserModel> tempUserInfo_list=null;

    SharedPreferences preferences;

    String uPhone=null;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        init();

            rememberCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb = true;
                        Log.i("remberCB: :", "Remmeber me checkbox checked");
                    } else {
                        cb = false;
                    }
                }
            });

            uLogingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: loginbutton");
                    if (!TextUtils.isEmpty(uEmail.getText().toString())&&
                            !TextUtils.isEmpty(uPasswordEditText.getText().toString()))
                    {
                        String email= uEmail.getText().toString();
                        String password=uPasswordEditText.getText().toString();
                        Log.i(TAG, "onClick: email: "+email+" pass: "+password);

                        login(email,password);

                    }else{
                        //fields are empty
                    }
                }
            });


            //if user don't have an any account
            //then user should create a new account
            nRegisterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(MainActivityLogin.this, UserRegisterActivity.class);
                    startActivity(registerIntent);
                }
            });


    }

    private void login(String email, String password) {
        Log.i(TAG, "login: login Auth called");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //yah we are in
                            mUser=mAuth.getCurrentUser();
                            Toast.makeText(MainActivityLogin.this, "Signed in successfull", Toast.LENGTH_SHORT).show();
                            Intent goHome=new Intent(MainActivityLogin.this,HomeActivity.class);
                            startActivity(goHome);
                            finish();

                        }else {
                            Toast.makeText(MainActivityLogin.this, "Signed failed!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    private void init() {

        uEmail = findViewById(R.id.u_email_et);
        uPasswordEditText = findViewById(R.id.u_password_et);
        rememberCB= findViewById(R.id.remember_me_checkbox);

        nRegisterButton = findViewById(R.id.register_button_id);
        uLogingButton = findViewById(R.id.login_btn);


       // mDatabase=new DBHelper(this);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser=firebaseAuth.getCurrentUser();

                if (mUser!=null)
                {
                    Log.i(TAG, "onAuthStateChanged: already sign in");
                    //Toast.makeText(MainActivityLogin.this, "Signed In", Toast.LENGTH_SHORT).show();
                    Intent goHome=new Intent(MainActivityLogin.this,HomeActivity.class);
                    startActivity(goHome);
                }else{
                    Toast.makeText(MainActivityLogin.this, "Not Signed In", Toast.LENGTH_SHORT).show();
                }

            }
        };

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();

        if (mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
