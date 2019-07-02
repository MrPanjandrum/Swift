package com.rrr.swift.temp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rrr.swift.temp2.Location;
import com.rrr.swift.R;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>
{

    Context context;

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
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
        //myViewHolder.desc.setText(list.get(position).getDealDisc());

    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public int getItemCount() {
        return list.size();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView address,desc;
        ImageView image;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            //desc = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);

        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
