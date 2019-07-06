package com.rrr.swift.LocationActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.R;

public class AddEditLocationActivity extends Activity
{

    private static final String TAG = "AddEditLocationActivity";

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");


    ////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_location);
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

    public void newLocation(View view)
    {
        Intent intent = new Intent(this, LocationEditDetailsActivity.class);
        //startActivity(intent);
    }
}
