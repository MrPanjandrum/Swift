package com.rrr.swift.TaskActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskDetailActivity extends Activity implements View.OnClickListener
{
    private static final String TAG = "TaskDetailActivity";

    Button startTaskBtn, stopTaskBtn, finishTaskBtn, submitCommentBtn;
    EditText taskComment;

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();
    private ArrayList<String> mTaskName = new ArrayList<>();
    private ArrayList<String> mTaskDescription = new ArrayList<>();
    private ArrayList<String> mTaskArea = new ArrayList<>();
    private ArrayList<String> mTaskStatus = new ArrayList<>();
    private ArrayList<Integer> mTaskNum = new ArrayList<>();
    private ArrayList<Long> mDateTest = new ArrayList<>();


    RecyclerView recyclerView;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef, myRef2, myRef3, myRef4;

    int commentID = 0;
    int taskStartID = 0;
    int timeID = 0;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

        recyclerView = findViewById(R.id.task_recyclerview);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);  //reverses order of comments displaying most recent on top
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        int taskNum = mTaskNum.get(0);
        Log.d(TAG, "Task Num: " + taskNum);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Tasks");
        myRef2 = mFirebaseDatabase.getReference().child("Tasks").child(String.valueOf(taskNum)).child("taskComments");
        myRef3 = mFirebaseDatabase.getReference().child("Tasks").child(String.valueOf(taskNum)).child("taskTime");
        myRef4 = mFirebaseDatabase.getReference().child("Tasks").child(String.valueOf(taskNum)).child("taskTime").child(String.valueOf(timeID)).child("taskStarted");


        Calendar calendar = Calendar.getInstance();
        final String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());     //gets date in format MM-dd-YY


        taskComment = (EditText) findViewById(R.id.et_task_comment);

        submitCommentBtn = (Button) findViewById(R.id.btn_comment_submit);
        startTaskBtn = (Button) findViewById(R.id.btn_task_start);
        stopTaskBtn = (Button) findViewById(R.id.btn_task_stop);
        finishTaskBtn = (Button) findViewById(R.id.btn_task_finish);

        startTaskBtn.setOnClickListener(this);
        stopTaskBtn.setOnClickListener(this);
        finishTaskBtn.setOnClickListener(this);


        myRef2.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    commentID = (int) dataSnapshot.getChildrenCount();
                    Log.d(TAG, "Comment ID:" + commentID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(TAG, "Failed to read value", databaseError.toException());
            }
        });

        myRef3.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    taskStartID = (int) dataSnapshot.getChildrenCount();
                    Log.d(TAG, "Task Time ID:" + taskStartID);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.w(TAG, "Failed to read value", databaseError.toException());
            }
        });

        submitCommentBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int taskNum = mTaskNum.get(0);
                String newTaskComment = taskComment.getText().toString().trim();

                myRef.child(String.valueOf(taskNum)).child("taskComments").child(String.valueOf(commentID+1)).child("commentText").setValue(newTaskComment);
                myRef.child(String.valueOf(taskNum)).child("taskComments").child(String.valueOf(commentID+1)).child("commentDate").setValue(currentDate);

                //to be used for user info
                // myRef.child(String.valueOf(taskNum)).child("taskComments").child(String.valueOf(commentID+1)).child("commentAuthor").setValue(date);

                //reset EditText
                taskComment.setText("");
            }
        });

//        RelativeLayout relativeLayout = findViewById(R.id.login_relative_layout);
//        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(2000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");

        FirebaseRecyclerAdapter<Location, CommentViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, CommentViewHolder>
        (
            Location.class, R.layout.recycler_view_task_comment_layout, CommentViewHolder.class, myRef2
        )
        {
    @Override
    protected void populateViewHolder(CommentViewHolder viewHolder, Location model, int position)
    {
       viewHolder.setCommentDetails(model.getCommentText(), model.getCommentDate());
    }
        };

    recyclerView.setAdapter(recyclerAdapter);

        //disables button corresponding to current status
        if(mTaskStatus.contains("in-progress"))
        {
            startTaskBtn.setEnabled(false);
        }
        else
            startTaskBtn.setEnabled(true);


        if(mTaskStatus.contains("stopped"))
        {
            stopTaskBtn.setEnabled(false);
        }
        else
            stopTaskBtn.setEnabled(true);


        if(mTaskStatus.contains("complete"))
        {
//            startTaskBtn.setEnabled(false);   //commented out for testing purposes only
//            stopTaskBtn.setEnabled(false);
            finishTaskBtn.setEnabled(false);
        }
        else
            finishTaskBtn.setEnabled(true);

    }

    public void onClick(View v)
    {
        long epoch = System.currentTimeMillis()/1000;       //gets current epoch time

        int taskNum = mTaskNum.get(0);

        switch (v.getId())
        {
            case R.id.btn_task_start:
//                Log.d(TAG, "Task Num: "+ mTaskNum);
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("in-progress");               //sets task status
//                myRef.child(String.valueOf(taskNum)).child("taskTime").child("taskStarted").setValue(epoch);    //stores start epoch date to firebase

                myRef.child(String.valueOf(taskNum)).child("taskTime").child(String.valueOf(taskStartID+1)).child("taskStarted").setValue(epoch);

                mTaskStatus.set(0, "in-progress");
                getIncomingIntent();
                startTaskBtn.setEnabled(false);        //disables start button
                onStart();                             //call onStart to re-enable previously disabled button
                break;

            case R.id.btn_task_stop:
//                Log.d(TAG, "MaxID: "+ mTaskNum);

                myRef.child(String.valueOf(taskNum)).child("taskTime").child(String.valueOf(taskStartID)).child("taskStopped").setValue(epoch);

//                myRef.child(String.valueOf(taskNum)).child("taskTime").child("taskStopped").setValue(epoch);
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("stopped");
                mTaskStatus.set(0,"stopped");
                getIncomingIntent();
                onStart();
                break;

            case R.id.btn_task_finish:
//                Log.d(TAG, "MaxID: "+ mTaskNum);
                myRef.child(String.valueOf(taskNum)).child("taskFinished").setValue(epoch);
                myRef.child(String.valueOf(taskNum)).child("taskStatus").setValue("complete");
                mTaskStatus.set(0,"complete");
                getIncomingIntent();
                calculateTaskTime(taskNum);
                onStart();

//                Context context = v.getContext();
//                Intent intent = new Intent(context, GalleryActivity.class);
//                intent.putExtra("address",mAddress);
//                intent.putExtra("address_image",mAddressImage);
//                context.startActivity(intent);

                Log.d(TAG,"Address Image: "+ mAddressImage.get(0));

                Intent taskFinishIntent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(taskFinishIntent);
                break;
        }
    }

    private void calculateTaskTime(int taskNum)
    {
        myRef.child(String.valueOf(taskNum)).child("taskTime").child(String.valueOf(taskStartID)).child("taskStarted").getKey();
    }

    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address") && getIntent().hasExtra("address_image") && getIntent().hasExtra("task_name") && getIntent().hasExtra("task_description")
                && getIntent().hasExtra("task_area") && getIntent().hasExtra("task_status") && getIntent().hasExtra("date_test"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddress = getIntent().getStringArrayListExtra("address");
            mAddressImage = getIntent().getStringArrayListExtra("address_image");
            mTaskName = getIntent().getStringArrayListExtra("task_name");
            mTaskDescription = getIntent().getStringArrayListExtra("task_description");
            mTaskArea = getIntent().getStringArrayListExtra("task_area");
            mTaskStatus = getIntent().getStringArrayListExtra("task_status");
            mTaskNum = getIntent().getIntegerArrayListExtra("task_num");
            mDateTest = (ArrayList<Long>) getIntent().getSerializableExtra("date_test");

            setTaskDetails(mAddress, mTaskName, mTaskDescription, mTaskArea, mTaskStatus, mTaskNum, mDateTest);

        }
        else
            Log.d(TAG, "getIncomingIntent: one or more intent extras NOT FOUND");
    }


    private void setTaskDetails(ArrayList<String> mAddress, ArrayList<String> mTaskName, ArrayList<String> mTaskDescription,
                                ArrayList<String> mTaskArea, ArrayList<String> mTaskStatus, ArrayList<Integer> mTaskNum, ArrayList<Long> mDateTest)
    {
        Log.d(TAG, "setTaskDetails: setting task name and details");

        TextView tskAddress = findViewById(R.id.address);
        TextView tskName = findViewById(R.id.taskName);
        TextView tskDescription = findViewById(R.id.taskDescription);
        TextView tskArea = findViewById(R.id.taskArea);
        ImageView tskStatus = findViewById(R.id.taskStatus);
        TextView textViewDate = findViewById(R.id.tv_task_created_date);

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

        long epoch = mDateTest.get(0);
        String date = new java.text.SimpleDateFormat("MMM dd yyyy").format(new java.util.Date (epoch*1000));
        //"MMM dd yyyy hh:mm:ss aa"

        tskAddress.setText(mAddress.get(0));
        tskName.setText(mTaskName.get(0));
        tskDescription.setText("Description: " + mTaskDescription.get(0));
        tskArea.setText("Area: " + mTaskArea.get(0));
        textViewDate.setText(String.valueOf(date));
    }

    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

}
