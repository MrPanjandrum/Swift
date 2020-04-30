package com.rrr.swift.MainActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rrr.swift.AuthActivity.LoginActivity;
import com.rrr.swift.LocationActivities.LocationActivity;
import com.rrr.swift.R;
import com.rrr.swift.ReportActivities.AddReportActivity;
import com.rrr.swift.SettingsActivities.SettingsActivity;
import com.rrr.swift.TaskActivities.TaskListActivity;

public class UserHomeActivity extends AppCompatActivity
{
    private static final String TAG = "UserHomeActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Log.d(TAG, "onCreate: started.");

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
//                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else
                {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Please Sign-In.");
                    Intent myIntent = new Intent(UserHomeActivity.this, LoginActivity.class);
                    UserHomeActivity.this.startActivity(myIntent);

                }
            }

        };

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) drawerLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

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

    public void openLocationActivity(View view)
    {
        Intent myIntent = new Intent(UserHomeActivity.this, LocationActivity.class);
        UserHomeActivity.this.startActivity(myIntent);
    }

    public void openSettingsActivity(View view)
    {
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settingsIntent);
    }


    public void openTaskListActivity(View view)
    {
        Intent settingsIntent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(settingsIntent);
    }

    public void openReportActivity(View view)
    {
        Intent settingsIntent = new Intent(getApplicationContext(), AddReportActivity.class);
        startActivity(settingsIntent);
    }

    public void userSignOut(View view)
    {
        mAuth.signOut();
        toastMessage("Signing out...");

        Intent settingsIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(settingsIntent);
    }

    private void toastMessage(String s) { Toast.makeText(this, s,Toast.LENGTH_SHORT).show(); }


}
