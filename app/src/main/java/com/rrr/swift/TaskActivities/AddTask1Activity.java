package com.rrr.swift.TaskActivities;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.LocationActivities.AddEditLocationViewHolder;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.Main2Activity;
import com.rrr.swift.R;

import java.util.ArrayList;

public class AddTask1Activity extends AppCompatActivity
{

    private static final String TAG = "AddTask1Activity";

    //private ArrayList<String> mAddress = new ArrayList<>();

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");



    ////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_1);
        Log.d(TAG,"onCreate: started.");

        recyclerView = findViewById(R.id.location_add_edit_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    ////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");
        FirebaseRecyclerAdapter<Location, AddEditLocationViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, AddEditLocationViewHolder>
                (
                        Location.class, R.layout.recycler_view_add_edit_location_layout, AddEditLocationViewHolder.class, reference

                )
        {
            @Override
            protected void populateViewHolder(AddEditLocationViewHolder viewHolder, Location model, int position)
            {
                viewHolder.setAddress(model.getAddress());

            }
        };

        recyclerView.setAdapter(recyclerAdapter);

    }


    public void openHomeActivity(View view)
    {
        Intent homeIntent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(homeIntent);
    }
}