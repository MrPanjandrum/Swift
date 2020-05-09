package com.rrr.swift.TaskActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.GalleryActivities.GalleryViewHolder;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;

public class TaskListActivity extends AppCompatActivity
{
    private static final String TAG = "TaskListActivity";

    private LinearLayoutManager mLayoutManager;

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Log.d(TAG, "onCreate: started.");

        recyclerView = findViewById(R.id.task_list_recyclerview);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(TaskListActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");

        FirebaseRecyclerAdapter<Location, GalleryViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, GalleryViewHolder>
                (
                        Location.class, R.layout.recycler_view_task_list_layout, GalleryViewHolder.class, reference
                )
        {
            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, Location model, int position)
            {
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    viewHolder.setTaskListDetails(model.getAddress(), model.getAddressImage(), model.getTaskName(), model.getTaskDescription(), model.getTaskArea(), model.getTaskStatus(), model.getTaskNum(), model.getDateTest());                  //sets taskname and description to viewholder
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
    }


    public void openHomeActivity(View view)
    {
        Intent homeIntent = new Intent(getApplicationContext(), UserHomeActivity.class);
        startActivity(homeIntent);
    }
}
