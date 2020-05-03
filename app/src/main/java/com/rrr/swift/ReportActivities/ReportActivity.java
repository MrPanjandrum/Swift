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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        taskLocation.setText(mAddress.get(0));

        recyclerView = findViewById(R.id.gallery_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

//        // Attach a listener to read the data at our posts reference
//        myRef.addValueEventListener(new ValueEventListener()
//        {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                long childrenCount = dataSnapshot.getChildrenCount();
//                Log.d(TAG, "Children Count: "+childrenCount);
////                Object value = dataSnapshot.getValue();
////                Log.d(TAG, "Data Snapshot Value: " + value);
////                showData(dataSnapshot);
////                totalTasks.setText(String.valueOf(mDateTest.get(0)));
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError)
//            {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

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
//        System.out.println("\nThe Date Difference Example");
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

            Log.d(TAG, "showData:  address: " + location.getAddress());
            Log.d(TAG, "showData:  dateTest: " + location.getDateTest());
            Log.d(TAG, "showData:  taskFinished: " + location.getTaskFinished());


            mDateTest.add(location.getDateTest());
            mTaskFinished.add(location.getTaskFinished());
            mSnapshotAddress.add(location.getAddress());

            if(mAddress.equals(mSnapshotAddress))
            {
                Log.d(TAG,"Address Match!!!");
            }
            else
            {Log.d(TAG, "No Match");}

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
