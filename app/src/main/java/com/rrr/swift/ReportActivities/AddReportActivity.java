package com.rrr.swift.ReportActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.LocationActivities.AddEditLocationReportViewHolder;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;


public class AddReportActivity extends AppCompatActivity
{

    private static final String TAG = "AddReportActivity";

    //private ArrayList<String> mAddress = new ArrayList<>();

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        Log.d(TAG,"onCreate: started.");

        recyclerView = findViewById(R.id.location_add_edit_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");
        FirebaseRecyclerAdapter<Location, AddEditLocationReportViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, AddEditLocationReportViewHolder>
                (
                        Location.class, R.layout.recycler_view_add_edit_location_layout, AddEditLocationReportViewHolder.class, reference

                )
        {
            @Override
            protected void populateViewHolder(AddEditLocationReportViewHolder viewHolder, Location model, int position)
            {
                viewHolder.setAddress(model.getAddress());
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
