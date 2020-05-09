package com.rrr.swift.GalleryActivities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.TaskDetailActivity;

import java.util.ArrayList;
import java.util.Date;

public class GalleryReportViewHolder extends RecyclerView.ViewHolder
{

    private static final String TAG = "GalleryViewHolder";
    View mView;

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();
    private ArrayList<String> mTaskName = new ArrayList<>();
    private ArrayList<String> mTaskDescription = new ArrayList<>();
    private ArrayList<String> mTaskArea = new ArrayList<>();
    private ArrayList<String> mTaskStatus = new ArrayList<>();
    private ArrayList<Integer> mTaskNum = new ArrayList<>();
    private ArrayList<Long> mDateTest = new ArrayList<>();  //created date
    private ArrayList<Long> mTaskFinished = new ArrayList<>();  //completed date


    public GalleryReportViewHolder(View itemView)
    {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = v.getContext();
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("address",mAddress);
                intent.putExtra("address_image",mAddressImage);
                intent.putExtra("task_name",mTaskName);
                intent.putExtra("task_description",mTaskDescription);
                intent.putExtra("task_area",mTaskArea);
                intent.putExtra("task_status",mTaskStatus);
                intent.putExtra("task_num", mTaskNum);
//                intent.putExtra("task_created", mTaskCreated);
                intent.putExtra("date_test", mDateTest);

                context.startActivity(intent);
            }
        });

    }

    public void setTaskDetails(String address, String addressImage, String taskName, String taskDescription,
                               String taskArea, String taskStatus, int taskNum, long dateTest, long taskFinished)       //used in ReportActivity
    {

        TextView tskName = mView.findViewById(R.id.recycler_task_name);
        ImageView tskStatus = mView.findViewById(R.id.recycler_task_status);
        TextView tskCreated = mView.findViewById(R.id.recycler_task_created_date);
        TextView tskFinished = mView.findViewById(R.id.recycler_task_finished_date);
        TextView tskLeadTime = mView.findViewById(R.id.recycler_task_lead_time);
        TextView tskCycleTime = mView.findViewById(R.id.recycler_task_cycle_time);

        tskName.setText(taskName);

        mAddress.add(address);
        mAddressImage.add(addressImage);
        mTaskName.add(taskName);
        mTaskDescription.add(taskDescription);
        mTaskArea.add(taskArea);
        mTaskStatus.add(taskStatus);
        mTaskNum.add(taskNum);
        mDateTest.add(dateTest);
        mTaskFinished.add(taskFinished);

        long creationDateMillisecs = (mDateTest.get(0) * 1000L);
        long completionDateMillisecs = mTaskFinished.get(0) * 1000L;
        long dateDifferenceInSeconds;

        double taskDaysDouble;
        double taskHoursDouble;
        double taskMinsDouble;

        int taskDaysInt;
        int taskHoursInt;
        int taskMinsInt;

        String leadTimeString;
        String cycleTimeString = "Test Time Here";

        Date creationDate = new Date(creationDateMillisecs);
        Date completionDate = new Date(completionDateMillisecs);

            dateDifferenceInSeconds = mTaskFinished.get(0) - mDateTest.get(0);   //calculates total length of time to task completion in seconds

            taskDaysDouble = dateDifferenceInSeconds / 86400.0d;                 //calculates number of days to task completion
            taskDaysInt = (int)taskDaysDouble;                                  //casts full number of days decimal to an int for display purposes

            taskHoursDouble = (taskDaysDouble - taskDaysInt) * 24;              //removes whole number, leaving decimal for num of hours calculation
            taskHoursInt = (int)taskHoursDouble;

            taskMinsDouble = (taskHoursDouble - taskHoursInt) * 60;
            taskMinsInt = (int)taskMinsDouble;

        leadTimeString = taskDaysInt +" days, " + taskHoursInt + " hours, " + taskMinsInt + " minutes";

        tskCreated.setText(String.valueOf(creationDate));
        tskFinished.setText(String.valueOf(completionDate));

        tskLeadTime.setText(leadTimeString);
        tskCycleTime.setText(cycleTimeString);


        if(taskStatus.contains("stopped"))                           //checks task status and sets corresponding traffic light image
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_red_64);
        }
        else if(taskStatus.contains("incomplete"))
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_yellow_64);
        }
        else if(taskStatus.contains("in-progress"))
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_green_64);
        }
        else if(taskStatus.contains("complete"))
        {
            tskStatus.setImageResource(R.drawable.icons8_checkmark_64);
        }
        else
        {
            tskStatus.setImageResource(R.drawable.icons8_traffic_light_64);
        }

    }

}
