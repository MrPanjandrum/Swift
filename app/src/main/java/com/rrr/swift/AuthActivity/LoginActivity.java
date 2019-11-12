package com.rrr.swift.AuthActivity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.HomeActivity;
import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.MainActivity.AdminHomeActivity;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;
import com.rrr.swift.temp.Main4Activity;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Main4Activity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignOut;
    private TextView regLink;
    Boolean isAdmin;

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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       // final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRef().child("mAdmin");
        final DatabaseReference mDatabase = database.getReference("Users");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }

        };




        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();

                /////////////////////////////////////////////////////////////////////
                // [START sign_in_with_email]
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information




                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    String admin = mDatabase.child(userId).child("mAdmin").toString();
                                    Log.e("mAdmin", admin);
                                   /* final DatabaseReference userRef = database.getReference("mAdmin");
                                    userRef.equalTo("mAdmin").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            final Boolean isAdmin = dataSnapshot.getValue(Boolean.class);
                                            Query query = userRef.equalTo("mAdmin");
                                            query.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    DataSnapshot adminChild = dataSnapshot.child("mAdmin");
                                                    String test = adminChild.getValue().toString();
                                                    Log.d("Query", test);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });*/

                                        Intent myIntent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                        LoginActivity.this.startActivity(myIntent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                // [END sign_in_with_email]
            }
        });
////////////////////////////////////////////////////////////////////

        //    if(!email.equals("") && !pass.equals(""))
        // {
        //  mAuth.signInWithEmailAndPassword(email,pass);
        //   Intent myIntent = new Intent(LoginActivity.this, AddEditLocationActivity.class);
        //  LoginActivity.this.startActivity(myIntent);
        // }
        //  else
        // {
        //    toastMessage("You didn't fill in all the fields");
        //  }

        // });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                toastMessage("Signing out...");
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
                finish();
            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void openRegisterActivity(View view) {

    }
//////////////////////////////////////////////////////////////////////////////////////////////

}





