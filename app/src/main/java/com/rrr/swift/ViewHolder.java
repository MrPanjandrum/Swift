package com.rrr.swift;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder
{

    View mView;

    public ViewHolder(@NonNull View itemView)
    {
        super(itemView);
        mView = itemView;
    }

    public void setDetails(String text, String image)
    {
        ImageView img = mView.findViewById(R.id.recycler_img);
        TextView txt = mView.findViewById(R.id.recycler_text);

        Picasso.get().load(image).into(img);
        txt.setText(text);
    }


}
