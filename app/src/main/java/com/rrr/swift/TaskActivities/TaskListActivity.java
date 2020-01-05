package com.rrr.swift.TaskActivities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.GalleryActivities.GalleryViewHolder;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.R;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity
{

    private static final String TAG = "TaskListActivity";

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();

    private Spinner mSpinner;
    private LinearLayoutManager mLayoutManager;

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Tasks");


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Log.d(TAG, "onCreate: started.");

//        mSpinner = findViewById(R.id.spinner1);
//
//        final List<String> sortBy = new ArrayList<String>();
//
//        sortBy.add(" Sort By ");
//        sortBy.add("Newest");
//        sortBy.add("Oldest");;
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sortBy);
//        mSpinner.setAdapter(dataAdapter);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() //spinner for setting list order
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                Toast.makeText(TaskListActivity.this,"Selected: "+ sortBy.get(position), Toast.LENGTH_SHORT).show();
//                if(sortBy.get(position).contains("Newest"))
//                {
//                    mLayoutManager = new LinearLayoutManager(TaskListActivity.this);
//                    mLayoutManager.setReverseLayout(true);
//                    mLayoutManager.setStackFromEnd(true);
//                    recyclerView.setLayoutManager(mLayoutManager);
//                }
//                else if(sortBy.get(position).contains("Oldest"))
//                {
//                    mLayoutManager = new LinearLayoutManager(TaskListActivity.this);
//                    mLayoutManager.setReverseLayout(false);  //reverses order of list
//                    mLayoutManager.setStackFromEnd(false);
//                    recyclerView.setLayoutManager(mLayoutManager);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });

        recyclerView = findViewById(R.id.task_list_recyclerview);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(TaskListActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            @RequiresApi(api = Build.VERSION_CODES.N)
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




}
