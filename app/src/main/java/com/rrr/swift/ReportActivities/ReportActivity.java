package com.rrr.swift.ReportActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.AddTask2Activity;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity
{
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private static final String TAG = "ReportActivity";

    private ArrayList<String> mAddress = new ArrayList<>();
    private TextView taskLocation, totalTasks;
    int taskNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d(TAG, "onCreate: started.");

        taskLocation = (TextView) findViewById(R.id.tv_selected_location);
        totalTasks = (TextView) findViewById(R.id.tv_tasks);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Tasks").child(String.valueOf(taskNum)).child("taskTime");

        getIncomingIntent();
        taskLocation.setText(mAddress.get(0));

        // Attach a listener to read the data at our posts reference
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                long childrenCount = dataSnapshot.getChildrenCount();
                Log.d(TAG, "Children Count: "+childrenCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//        date.setText(String.valueOf(createDate));
//        Calendar calendar1 = Calendar.getInstance();
//        Calendar calendar2 = Calendar.getInstance();
//
//        long milliseconds1 = calendar1.getTimeInMillis();
//        long milliseconds2 = calendar2.getTimeInMillis();
//
//        long diff = milliseconds2 - milliseconds1;
//        long diffSeconds = diff / 1000;
//        long diffMinutes = diff / (60 * 1000);
//        long diffHours = diff / (60 * 60 * 1000);
//        long diffDays = diff / (24 * 60 * 60 * 1000);
//
//        System.out.println("\nThe Date Different Example");
//
//        System.out.println("Cal 1: "+milliseconds1+"  Cal2: "+milliseconds2);
//
//        System.out.println("Time in milliseconds: " + diff + " milliseconds.");
//
//        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//
//        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//
//        System.out.println("Time in hours: " + diffHours + " hours.");
//
//        System.out.println("Time in days: " + diffDays + " days.");

    }



    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddress = getIntent().getStringArrayListExtra("address");

            Intent intent = new Intent(this, AddTask2Activity.class);
            intent.putExtra("address",mAddress);

        }
    }







}
