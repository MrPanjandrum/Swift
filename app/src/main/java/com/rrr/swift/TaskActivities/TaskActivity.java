package com.rrr.swift.TaskActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.rrr.swift.R;

import java.util.ArrayList;

public class TaskActivity extends Activity
{

    private static final String TAG = "TaskActivity";

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTaskName = new ArrayList<>();
    private ArrayList<String> mTaskDescription = new ArrayList<>();
    private ArrayList<String> mTaskArea = new ArrayList<>();



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();


    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(/*getIntent().hasExtra("address") && */getIntent().hasExtra("task_name") && getIntent().hasExtra("task_description"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddress = getIntent().getStringArrayListExtra("address");
            mTaskName = getIntent().getStringArrayListExtra("task_name");
            mTaskDescription = getIntent().getStringArrayListExtra("task_description");
            mTaskArea = getIntent().getStringArrayListExtra("task_area");

            setTaskDetails(mAddress, mTaskName, mTaskDescription, mTaskArea);
        }
        else
            Log.d(TAG, "getIncomingIntent: one or more intent extras NOT FOUND");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void setTaskDetails(ArrayList<String> mAddress, ArrayList<String> mTaskName, ArrayList<String> mTaskDescription, ArrayList<String> mTaskArea)
    {
        Log.d(TAG, "setTaskDetails: setting task name and details");

        TextView tskAddress = findViewById(R.id.address);
        TextView tskName = findViewById(R.id.taskName);
        TextView tskDescription = findViewById(R.id.taskDescription);
        TextView tskArea = findViewById(R.id.taskArea);



        tskAddress.setText(mAddress.get(0));
        tskName.setText(mTaskName.get(0));
        tskDescription.setText(mTaskDescription.get(0));
        tskArea.setText(mTaskArea.get(0));

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////







}
