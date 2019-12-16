package com.rrr.swift.AuthActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;
import com.rrr.swift.RegistrationActivity.RegActivity;

public class LoginActivity extends AppCompatActivity
{

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignOut;
    private TextView regLink;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
                final String email = mEmail.getText().toString();
                final String pass = mPassword.getText().toString();

//                toastMessage("Login Clicked");
                if(email.equals("") || pass.equals(""))
                    toastMessage("You didn't fill in all the fields");

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
                {
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    if (!task.isSuccessful())
                                    {
                                        Log.w(TAG, "signInWithEmail", task.getException());
                                        Toast.makeText(LoginActivity.this, "Sign-In failed.",
                                                Toast.LENGTH_SHORT).show();
                                    } else
                                        {
                                        mAuth.signInWithEmailAndPassword(email,pass);
                                        Intent myIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                                        LoginActivity.this.startActivity(myIntent);
                                        }
                                }
                            });
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
        startActivity (new Intent(LoginActivity.this, RegActivity.class));
        finish();
    }




}
