package com.rrr.swift;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rrr.swift.LocationActivities.LocationActivity;

public class HomeActivity extends Activity
{

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started.");



    }

    public void openLocationActivity(View view)
    {
        Intent myIntent = new Intent(HomeActivity.this, LocationActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }


}
