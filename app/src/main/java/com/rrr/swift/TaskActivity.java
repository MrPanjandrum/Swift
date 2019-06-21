package com.rrr.swift;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskActivity extends Activity
{

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Posts");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.task_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerAdapter<RecyclerData, ViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<RecyclerData, ViewHolder>
                (
                        RecyclerData.class, R.layout.recycler_view_layout, ViewHolder.class, reference
                )
        {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, RecyclerData model, int position)
            {
            viewHolder.setDetails(model.getText(),model.getImg());
            }
        };

        recyclerView.setAdapter(recyclerAdapter);

    }
}
