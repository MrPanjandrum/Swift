package com.rrr.swift.GalleryActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.TaskActivity;

import java.util.ArrayList;

public class GalleryViewHolder extends RecyclerView.ViewHolder
{

    View mView;

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTaskName = new ArrayList<>();
    private ArrayList<String> mTaskDescription = new ArrayList<>();
    private ArrayList<String> mTaskArea = new ArrayList<>();

    public GalleryViewHolder(View itemView)
    {
        super(itemView);

        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = v.getContext();
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("address",mAddress);
                intent.putExtra("task_name",mTaskName);
                intent.putExtra("task_description",mTaskDescription);
                intent.putExtra("task_area",mTaskArea);
                context.startActivity(intent);
                //Toast.makeText(context,"Clicked: "+mTaskName, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setTaskDetails(String address, String taskName, String taskDescription, String taskArea)
    {

        TextView tskName = mView.findViewById(R.id.recycler_task_name);
        TextView tskDescription = mView.findViewById(R.id.recycler_task_description);

        tskName.setText(taskName);
        tskDescription.setText(taskDescription);

        mAddress.add(address);
        mTaskName.add(taskName);
        mTaskDescription.add(taskDescription);
        mTaskArea.add(taskArea);

    }




}




