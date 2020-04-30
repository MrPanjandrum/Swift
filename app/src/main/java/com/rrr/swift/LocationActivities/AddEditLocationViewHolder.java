package com.rrr.swift.LocationActivities;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrr.swift.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AddEditLocationViewHolder extends RecyclerView.ViewHolder
{

    View mView;

    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mAddressImage = new ArrayList<>();



    public AddEditLocationViewHolder(final View itemView)
    {
        super(itemView);

        mView = itemView;
//        itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, AddTask2Activity.class);
//                intent.putExtra("address",mAddress);
//                intent.putExtra("address_image",mAddressImage);
//                context.startActivity(intent);
//                Toast.makeText(context,"Clicked: "+ mAddress, Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void setLocationDetails(String address, String addressImage)
    {
        ImageView img = mView.findViewById(R.id.recycler_img);
        TextView txt = mView.findViewById(R.id.recycler_text);

        Picasso.get().load(addressImage).into(img);
        txt.setText(address);

        mAddress.add(address);
        mAddressImage.add(addressImage);

    }


    public void setAddress(String address)
    {
        TextView txt = mView.findViewById(R.id.recycler_text);
        txt.setText(address);

        mAddress.add(address);
    }


}

