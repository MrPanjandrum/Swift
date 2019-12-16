package com.rrr.swift.TaskActivities;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rrr.swift.R;

import java.util.ArrayList;

public class CommentViewHolder extends RecyclerView.ViewHolder
{
    private static final String TAG = "GalleryViewHolder";
    View mView;

    private ArrayList<String> mCommentText = new ArrayList<>();
    private ArrayList<String> mCommentDate = new ArrayList<>();


    public CommentViewHolder(View itemView)
    {
        super(itemView);
        mView = itemView;

        itemView.setOnLongClickListener(new View.OnLongClickListener()  //added for future edit/deleting of comments
        {
            @Override
            public boolean onLongClick(View v)
            {
//                Log.d(TAG,"Long Click: Working.");
                return false;
            }
        });

    }

    public void setCommentDetails(String commentText, String commentDate)
    {
        TextView cmntText = mView.findViewById(R.id.recycler_comment);
        TextView dteText = mView.findViewById(R.id.recycler_comment_date);

        cmntText.setText(commentText);
        dteText.setText(commentDate+": ");

        mCommentText.add(commentText);
        mCommentDate.add(commentDate);
    }







}
