package com.rrr.swift.SearchExampleActivites;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrr.swift.LocationActivities.Location;
import com.rrr.swift.R;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>
{

    ArrayList<Location> list;



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public AdapterClass (ArrayList<Location> list)
    {
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup,false);
        return new MyViewHolder(view);
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position)
    {
        myViewHolder.address.setText(list.get(position).getAddress());

    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public int getItemCount() {
        return list.size();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView address;
        ImageView image;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            image = itemView.findViewById(R.id.image);

        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
