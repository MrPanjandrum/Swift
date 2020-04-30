package com.rrr.swift.TaskActivities;



import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;

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
        FirebaseRecyclerAdapter<Location, AddTaskViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, AddTaskViewHolder>
                (
                        Location.class, R.layout.recycler_view_add_edit_location_layout, AddTaskViewHolder.class, reference

                )
        {
            @Override
            protected void populateViewHolder(AddTaskViewHolder viewHolder, Location model, int position)
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
