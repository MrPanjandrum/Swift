package com.rrr.swift.TaskActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.R;
import com.rrr.swift.SettingsActivities.SettingsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "TaskActivity";

    Button startTaskBtn, stopTaskBtn, finishTaskBtn;

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTaskName = new ArrayList<>();
    private ArrayList<String> mTaskDescription = new ArrayList<>();
    private ArrayList<String> mTaskArea = new ArrayList<>();
    private ArrayList<String> mTaskStatus = new ArrayList<>();
    private ArrayList<Integer> mTaskNum = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    int maxID = 0;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Log.d(TAG, "onCreate: started.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Tasks");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
//        Date date = new Date();
//        TextView textViewTime = findViewById(R.id.tv_task_start_time);
//        textViewTime.setText(simpleDateFormat.format(date));


        TextView textViewDate = findViewById(R.id.tv_task_created_date);    //temp created date for testing
        textViewDate.setText(currentDate);


        getIncomingIntent();

        startTaskBtn = (Button) findViewById(R.id.btn_task_start);
        stopTaskBtn = (Button) findViewById(R.id.btn_task_stop);
        finishTaskBtn = (Button) findViewById(R.id.btn_task_finish);

        startTaskBtn.setOnClickListener(this);
        stopTaskBtn.setOnClickListener(this);
        finishTaskBtn.setOnClickListener(this);



        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    maxID = (int) dataSnapshot.getChildrenCount();
                }
//                Object value = dataSnapshot.getValue();
//                Log.d(TAG, "Value is:" + value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(TAG, "Failed to read value", databaseError.toException());
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(mTaskStatus.contains("in-progress"))
        {
            startTaskBtn.setEnabled(false);
        }
        else
            startTaskBtn.setEnabled(true);


        if(mTaskStatus.contains("incomplete"))
        {
            stopTaskBtn.setEnabled(false);
        }
        else
            stopTaskBtn.setEnabled(true);


        if(mTaskStatus.contains("complete"))
        {
            finishTaskBtn.setEnabled(false);
        }
        else
            finishTaskBtn.setEnabled(true);


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void onClick(View v)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
        Date date = new Date();

        int taskNum = mTaskNum.get(0);

        switch (v.getId())
        {
            case R.id.btn_task_start:
//                Log.d(TAG, "Task Num: "+ mTaskNum);
                myRef.child(String.valueOf(taskNum)).child("taskStarted").setValue(date);           //stores task date info to firebase
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("in-progress");   //sets task status
                mTaskStatus.set(0,"in-progress");
                getIncomingIntent();
                startTaskBtn.setEnabled(false);                                                     //disables start button
                onStart();                                                                          //call onStart to re-enable previously disabled button
                break;

            case R.id.btn_task_stop:
//                Log.d(TAG, "MaxID: "+ mTaskNum);
                myRef.child(String.valueOf(taskNum)).child("taskStopped").setValue(date);
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("incomplete");
                mTaskStatus.set(0,"incomplete");
                getIncomingIntent();
                onStart();
                break;

            case R.id.btn_task_finish:
//                Log.d(TAG, "MaxID: "+ mTaskNum);
                myRef.child(String.valueOf(taskNum)).child("taskFinished").setValue(date);
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("complete");
                mTaskStatus.set(0,"complete");
                getIncomingIntent();
                onStart();
                break;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address") && getIntent().hasExtra("task_name") && getIntent().hasExtra("task_description")
                && getIntent().hasExtra("task_area") && getIntent().hasExtra("task_status"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddress = getIntent().getStringArrayListExtra("address");
            mTaskName = getIntent().getStringArrayListExtra("task_name");
            mTaskDescription = getIntent().getStringArrayListExtra("task_description");
            mTaskArea = getIntent().getStringArrayListExtra("task_area");
            mTaskStatus = getIntent().getStringArrayListExtra("task_status");
            mTaskNum = getIntent().getIntegerArrayListExtra("task_num");

            setTaskDetails(mAddress, mTaskName, mTaskDescription, mTaskArea, mTaskStatus, mTaskNum);

        }
        else
            Log.d(TAG, "getIncomingIntent: one or more intent extras NOT FOUND");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void setTaskDetails(ArrayList<String> mAddress, ArrayList<String> mTaskName, ArrayList<String> mTaskDescription,
                                ArrayList<String> mTaskArea, ArrayList<String> mTaskStatus, ArrayList<Integer> mTaskNum)
    {
        Log.d(TAG, "setTaskDetails: setting task name and details");

        TextView tskAddress = findViewById(R.id.address);
        TextView tskName = findViewById(R.id.taskName);
        TextView tskDescription = findViewById(R.id.taskDescription);
        TextView tskArea = findViewById(R.id.taskArea);
        ImageView tskStatus = findViewById(R.id.taskStatus);


        //checks task status from firebase, sets imageview to corresponding status-light color
        if(mTaskStatus.contains("stopped"))
        {
            Log.d(TAG, "Task Status: "+ mTaskStatus);
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_red_64);
        }
        else if(mTaskStatus.contains("incomplete"))
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_yellow_64);
        }
        else if(mTaskStatus.contains("in-progress"))
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_green_64);
        }
        else if(mTaskStatus.contains("complete"))
        {
            tskStatus.setImageResource(R.drawable.icons8_checkmark_64);
        }
        else
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_64);
        }


        tskAddress.setText(mAddress.get(0));
        tskName.setText(mTaskName.get(0));
        tskDescription.setText("Description: " + mTaskDescription.get(0));
        tskArea.setText("Area: " + mTaskArea.get(0));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////







}
