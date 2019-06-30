package com.rrr.swift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewHolder extends RecyclerView.ViewHolder
{

    View mView;
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();

    private Context mContext;




    public ViewHolder(final View itemView)
    {
        super(itemView);
        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Context context = v.getContext();
               Intent intent = new Intent(context, GalleryActivity.class);
               intent.putExtra("address",mAddress);
               intent.putExtra("address_image",mAddressImage);
               context.startActivity(intent);
               //Toast.makeText(context,"Cicked: "+ mAddress, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setDetails(String text, String image)
    {
        ImageView img = mView.findViewById(R.id.recycler_img);
        TextView txt = mView.findViewById(R.id.recycler_text);

        Picasso.get().load(image).into(img);
        txt.setText(text);
        mAddress.add(text);
        mAddressImage.add(image);

    }






}
