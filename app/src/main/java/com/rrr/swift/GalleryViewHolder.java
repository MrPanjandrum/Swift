package com.rrr.swift;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class GalleryViewHolder extends RecyclerView.ViewHolder
{

    View mView;

    public GalleryViewHolder(View itemView)
    {
        super(itemView);
        mView = itemView;
    }

    public void setDetail(String taskName, String taskDescription)
    {



        TextView tskName = mView.findViewById(R.id.recycler_task_name);
        TextView tskDescription = mView.findViewById(R.id.recycler_task_description);

        tskName.setText(taskName);
        tskDescription.setText(taskDescription);

    }




}




