package com.rrr.swift.LocationActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.R;

import java.util.ArrayList;


public class LocationEditDetailsActivity extends Activity
{

    private static final String TAG = "LocEditDetailsActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");

    EditText mAptNum;

    private ArrayList<String> mAddress = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_edit_details);

        mAptNum = (EditText)findViewById(R.id.et_apt_num);

        getIncomingIntent();

    }


    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

             mAddress = getIntent().getStringArrayListExtra("address");

             Log.d(TAG,"intent address is: " + mAddress);

             setEditText(mAddress);

        }
    }

    private void setEditText(ArrayList<String> mAddress)
    {
        Log.d(TAG, "setEditText: setting the address to edit text field.");

        EditText editText = findViewById(R.id.et_address);
        editText.setText(String.valueOf(mAddress));
    }


}
