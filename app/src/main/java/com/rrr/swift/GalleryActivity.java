package com.rrr.swift;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rrr.swift.temp2.Location;

import java.util.ArrayList;
import java.util.Iterator;


public class GalleryActivity extends Activity
{

    private static final String TAG = "GalleryActivity";

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Tasks");





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        //Log.d(TAG, "onCreate: started.");

        recyclerView = findViewById(R.id.gallery_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        Log.d(TAG, "onCreate: started.");
        getIncomingIntent();

    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    protected void onStart()
    {
        super.onStart();



        FirebaseRecyclerAdapter<Location, GalleryViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<Location, GalleryViewHolder>
                (
                     Location.class, R.layout.recycler_view_task_layout, GalleryViewHolder.class, reference
                )
        {

            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, Location model, int position)
            {

    if(mAddress.contains(model.getAddress()))
        {
            viewHolder.setDetail(model.getTaskName(), model.getTaskDescription());
            //mAddress = model.getAddress();
            System.out.println(mAddress);
            System.out.println(model.getAddress());
        }


           }
        };

        recyclerView.setAdapter(recyclerAdapter);


    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address_image") && getIntent().hasExtra("address"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddressImage = getIntent().getStringArrayListExtra("address_image");
            mAddress = getIntent().getStringArrayListExtra("address");

            setImage(mAddressImage, mAddress);




        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void setImage(ArrayList<String> mAddressImage, ArrayList<String> mAddress)
    {
        Log.d(TAG, "setImage: setting the image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(mAddress.get(0));

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(mAddressImage.get(0)
                )
                .into(image);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//    private int determineAddress()
//    {
//
//        mAddress = getIntent().getStringArrayListExtra("address"); //123 Example St, NY
//        for (int i=0;i<=5;i++)
//        {
////            if()
////            {
////
////            }
//        }
//
//
//        return num;
//    }







}



