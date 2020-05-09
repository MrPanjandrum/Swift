package com.rrr.swift.ReportActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.GalleryActivities.GalleryReportViewHolder;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.AddTask2Activity;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity
{
    private static final String TAG = "ReportActivity";

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Tasks");

    private ArrayList<Long> mDateTest = new ArrayList<>();
    private ArrayList<Long> mTaskFinished = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mSnapshotAddress = new ArrayList<>();

    private TextView taskLocation, totalTasks, completedTasks;

    int numTasks;
    int numTasksCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();

        taskLocation = findViewById(R.id.tv_selected_location);
        totalTasks = findViewById(R.id.tv_tasks);
        completedTasks = findViewById(R.id.tv_completed_task_num);
        recyclerView = findViewById(R.id.gallery_recyclerview);

        taskLocation.setText(mAddress.get(0));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));




        reference.addValueEventListener(new ValueEventListener()
        {
//            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                long childrenCount = dataSnapshot.getChildrenCount();      //gets total number of tasks in firebase
//                Log.d(TAG, "Children Count: "+childrenCount);
                Object value = dataSnapshot.getValue();
//                Log.d(TAG, "Data Snapshot Value: " + value);
                showData(dataSnapshot);
                totalTasks.setText(String.valueOf(mDateTest.get(0)));

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }




    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");

        FirebaseRecyclerAdapter<Location, GalleryReportViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, GalleryReportViewHolder>
                (
                        Location.class, R.layout.recycler_view_report_layout, GalleryReportViewHolder.class, reference
                )
        {

            @SuppressLint("NewApi")
            @Override
            protected void populateViewHolder(GalleryReportViewHolder viewHolder, Location model, int position)
            {
                if(mAddress.contains(model.getAddress()) && model.getTaskStatus().contains("complete"))   //checks for incomplete tasks matching selected location
                {
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    viewHolder.setTaskDetails(model.getAddress(), model.getAddressImage(), model.getTaskName(), model.getTaskDescription(), model.getTaskArea(),
                            model.getTaskStatus(), model.getTaskNum(), model.getDateTest(), model.getTaskFinished());     //sets details to viewholder

                    numTasksCompleted += 1;
                    numTasks +=1;

                    totalTasks.setText(String.valueOf(numTasks));
                    completedTasks.setText(String.valueOf(numTasksCompleted));
                }
                else if(mAddress.contains(model.getAddress()))
                {
                    numTasks += 1;
                    totalTasks.setText(String.valueOf(numTasks));
                    completedTasks.setText(String.valueOf(numTasksCompleted));

                    viewHolder.itemView.setVisibility(View.GONE);
                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));    //hides layouts not matching selected location
                }
                else
                {
                    viewHolder.itemView.setVisibility(View.GONE);
                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));    //hides layouts not matching selected location
                }
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
    }







    private void showData(DataSnapshot dataSnapshot)    //currently unused, testing for use with task time calculation
    {

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Location location = new Location();
            location.setDateTest(ds.getValue(Location.class).getDateTest());
            location.setTaskFinished(ds.getValue(Location.class).getTaskFinished());
            location.setAddress(ds.getValue(Location.class).getAddress());

            mDateTest.add(location.getDateTest());
            mTaskFinished.add(location.getTaskFinished());
            mSnapshotAddress.add(location.getAddress());

//        long epochTimeInSec;
//
//        int taskDaysInt;
//        double taskDaysDouble;
//
//        int taskHoursInt;
//        double taskHoursDouble;
//
//        int taskMinsInt;
//        double taskMinsDouble;

//            Log.d(TAG, "showData:  address: " + location.getAddress());
//            Log.d(TAG, "showData:  dateTest: " + location.getDateTest());
//            Log.d(TAG, "showData:  taskFinished: " + location.getTaskFinished());

//            epochTimeInSec = mTaskFinished.get(0) - mDateTest.get(0);   //calculates total length of time to task completion in seconds
//
//            taskDaysDouble = epochTimeInSec / 86400.0d;                 //calculates number of days to task completion
//            taskDaysInt = (int)taskDaysDouble;                          //casts full number of days decimal to int for display purposes
//
//            taskHoursDouble = (taskDaysDouble - taskDaysInt) * 24;      //removes whole number, leaving decimal for num of hours calculation
//            taskHoursInt = (int)taskHoursDouble;
//
//            taskMinsDouble = (taskHoursDouble - taskHoursInt) * 60;
//            taskMinsInt = (int)taskMinsDouble;
//
//            Log.d(TAG, "epochTimeInSec: "+ epochTimeInSec);
//            Log.d(TAG, "taskDaysDouble: "+ taskDaysDouble);
//            Log.d(TAG, "taskDaysInt: "+ taskDaysInt);
//            Log.d(TAG, "taskHoursDouble: "+ taskHoursDouble);
//            Log.d(TAG, "taskHoursInt: "+ taskHoursInt);
//            Log.d(TAG, "taskMinsDouble: "+ taskMinsDouble);
//            Log.d(TAG, "taskMinsInt: "+ taskMinsInt);

//            if(mAddress.equals(mSnapshotAddress))
//            {
//                Log.d(TAG,"Address Match!");
//                Log.d(TAG,"Epoch Difference is: " + epochTimeInSec);
//                Log.d(TAG,"Task Completed in: " + taskDaysInt + " days, " + taskHoursInt + " hours, " + taskMinsInt + (" minutes"));
//
//            }
//            else
//            {Log.d(TAG, "No Match");}

        }
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

    public void openHomeActivity(View view)
    {
        Intent addReport = new Intent(getApplicationContext(), AddReportActivity.class);
        startActivity(addReport);
    }
}
