package com.rrr.swift.LocationActivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.R;


public class LocationActivity extends Activity
{

    private static final String TAG = "LocationActivity";

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");




   ///////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.d(TAG, "onCreate: started.");

        recyclerView = findViewById(R.id.location_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }





////////////////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");
        FirebaseRecyclerAdapter<Location, LocationViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, LocationViewHolder>
                (
                        Location.class, R.layout.recycler_view_layout, LocationViewHolder.class, reference

                )
        {
            @Override
            protected void populateViewHolder(LocationViewHolder viewHolder, Location model, int position)
            {
                viewHolder.setLocationDetails(model.getAddress(),model.getAddressImage());

            }
        };

        recyclerView.setAdapter(recyclerAdapter);


    }



    ///////////////////////////////////////////////////////////////////////////////////////







}