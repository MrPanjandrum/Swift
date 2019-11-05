package com.rrr.swift.AuthActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.MainActivity.AdminHomeActivity;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Main4Activity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignOut;
    private TextView regLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declarations
        mEmail = (EditText) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        btnSignIn = (Button) findViewById(R.id.login_btn);
        btnSignOut = (Button) findViewById(R.id.logout_btn);
        regLink = findViewById(R.id.regAccount);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("mEmail");
        mFirebaseDatabase = mFirebaseInstance.getReference("mPassword");

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else
                {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }

        };

        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();

                    if(email.equals("admin@local404.com")) {

                        mAuth.signInWithEmailAndPassword(email, pass);
                        Intent myIntent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        LoginActivity.this.startActivity(myIntent);
                    }

                    else if(!email.equals("") && !pass.equals("") && email == mFirebaseDatabase.child("User").child().getKey().child("mEmail")) {
                        mAuth.signInWithEmailAndPassword(email, pass);
                        Intent myIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                        LoginActivity.this.startActivity(myIntent);
                    }

                    else
                    {
                        toastMessage("You didn't fill in all the fields");
                    }



            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                toastMessage("Signing out...");
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(LoginActivity.this, RegActivity.class));
                finish();
            }
        });

    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

    public void openRegisterActivity(View view)
    {

    }


}

