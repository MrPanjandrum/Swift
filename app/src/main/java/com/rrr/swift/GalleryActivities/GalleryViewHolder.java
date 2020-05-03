package com.rrr.swift.GalleryActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.TaskDetailActivity;

import java.util.ArrayList;

public class GalleryViewHolder extends RecyclerView.ViewHolder
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
    private ArrayList<Long> mHours = new ArrayList<>();  //completed date

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
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("address",mAddress);
                intent.putExtra("address_image",mAddressImage);
                Log.d(TAG,"Address Image: " + mAddressImage);
                intent.putExtra("task_name",mTaskName);
                intent.putExtra("task_description",mTaskDescription);
                intent.putExtra("task_area",mTaskArea);
                intent.putExtra("task_status",mTaskStatus);
                intent.putExtra("task_num", mTaskNum);
//                intent.putExtra("task_created", mTaskCreated);
                intent.putExtra("date_test", mDateTest);

                context.startActivity(intent);
                //Toast.makeText(context,"Clicked: "+mTaskName, Toast.LENGTH_LONG).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTaskDetails(String address, String addressImage, String taskName, String taskDescription,
                               String taskArea, String taskStatus, int taskNum, long dateTest, long taskFinished)
    {

        TextView tskName = mView.findViewById(R.id.recycler_task_name);
        TextView tskDescription = mView.findViewById(R.id.recycler_task_description);
        ImageView tskStatus = mView.findViewById(R.id.recycler_task_status);
        TextView tskCreated = mView.findViewById(R.id.recycler_task_created_date);
        TextView tskFinished = mView.findViewById(R.id.recycler_task_finished_date);
        TextView tskTimeDays = mView.findViewById(R.id.recycler_task_elapsed_time_days);
        TextView tskTimeHrs = mView.findViewById(R.id.recycler_task_elapsed_time_hrs);

        tskName.setText(taskName);
        tskDescription.setText(taskDescription);


        mAddress.add(address);
        mAddressImage.add(addressImage);
        mTaskName.add(taskName);
        mTaskDescription.add(taskDescription);
        mTaskArea.add(taskArea);
        mTaskStatus.add(taskStatus);
        mTaskNum.add(taskNum);
        mDateTest.add(dateTest);
        mTaskFinished.add(taskFinished);



        long milliseconds1 = mDateTest.get(0);
        long milliseconds2 = mTaskFinished.get(0);


        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.println("Date Difference Example");

        System.out.println("millis 1: "+milliseconds1+"  millis: "+milliseconds2);

        System.out.println("Time in milliseconds: " + diff + " milliseconds.");

        System.out.println("Time in seconds: " + diffSeconds + " seconds.");

        System.out.println("Time in minutes: " + diffMinutes + " minutes.");

        System.out.println("Time in hours: " + diffHours + " hours.");

        System.out.println("Time in days: " + diffDays + " days.");

mHours.add(diffHours);


//        tskCreated.setText(String.valueOf(dateTest));
//        tskFinished.setText(String.valueOf(taskFinished));
//        tskTimeDays.setText(String.valueOf(diffDays));
//        tskTimeHrs.setText(String.valueOf(diffHours));
//        tskTimeMins.setText(String.valueOf(diffMinutes));




        if(taskStatus.contains("stopped"))
        {
//            Log.d(TAG, "Task Status: "+ taskStatus);
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


    public void setTaskListDetails(String address, String addressImage, String taskName, String taskDescription, String taskArea, String taskStatus, int taskNum, long dateTest)
    {

        TextView tskName = mView.findViewById(R.id.recycler_task_name);
        ImageView tskStatus = mView.findViewById(R.id.recycler_task_status);
        TextView tskLocation = mView.findViewById(R.id.recycler_task_location);
        TextView tskArea = mView.findViewById(R.id.recycler_task_area);
        TextView tskCreateDate = mView.findViewById(R.id.recycler_task_create_date);

        mAddress.add(address);
        mAddressImage.add(addressImage);
        mTaskName.add(taskName);
        mTaskDescription.add(taskDescription);
        mTaskArea.add(taskArea);
        mTaskStatus.add(taskStatus);
        mTaskNum.add(taskNum);
        mDateTest.add(dateTest);
        long epoch = mDateTest.get(0);

        String date = new java.text.SimpleDateFormat("MMM dd yyyy").format(new java.util.Date (epoch*1000));

        tskName.setText(taskName);
        tskLocation.setText(address);
        tskArea.setText(taskArea);
        tskCreateDate.setText(String.valueOf(date));
        

        //Log.d(TAG, "Task Status: "+ taskStatus);
        if(taskStatus != null && taskStatus.contains("stopped"))
        {
//            Log.d(TAG, "Task Status: "+ taskStatus);
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




