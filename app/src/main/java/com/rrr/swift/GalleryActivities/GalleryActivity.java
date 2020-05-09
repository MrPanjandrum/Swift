package com.rrr.swift.GalleryActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryActivity extends Activity
{
    private static final String TAG = "GalleryActivity";

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Tasks");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");

        recyclerView = findViewById(R.id.gallery_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        getIncomingIntent();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: started.");

        getIncomingIntent();

        FirebaseRecyclerAdapter<Location, GalleryViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, GalleryViewHolder>
                (
                     Location.class, R.layout.recycler_view_task_layout, GalleryViewHolder.class, reference
                )
        {
            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, Location model, int position)
            {
    if(mAddress.contains(model.getAddress()) && !model.getTaskStatus().contains("complete"))   //checks for incomplete tasks matching selected location
        {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            viewHolder.setTaskDetails(model.getAddress(), model.getAddressImage(), model.getTaskName(), model.getTaskDescription(), model.getTaskArea(),
                                        model.getTaskStatus(), model.getTaskNum(), model.getDateTest(), model.getTaskFinished());     //sets details to viewholder
        }
    else
        {
        viewHolder.itemView.setVisibility(View.GONE);
        viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));    //hides layouts not matching selected location
        }
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
    }

    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address_image") && getIntent().hasExtra("address"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddressImage = getIntent().getStringArrayListExtra("address_image");
            mAddress = getIntent().getStringArrayListExtra("address");
            Log.d(TAG,"Address Image: " + mAddressImage);

            setImage(mAddressImage, mAddress);

        }
    }

    private void setImage(ArrayList<String> mAddressImage, ArrayList<String> mAddress)
    {
        Log.d(TAG, "setImage: setting image and name");

        mAddress.add(String.valueOf(mAddress));
        mAddressImage.add(String.valueOf(mAddressImage));

        TextView name = findViewById(R.id.image_description);
        name.setText(mAddress.get(0));

        ImageView image = findViewById(R.id.image);
        Picasso.get().load(mAddressImage.get(0)).into(image);
    }


}



