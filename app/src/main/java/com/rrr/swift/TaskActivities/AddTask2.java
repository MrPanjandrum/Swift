package com.rrr.swift.TaskActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.LocationActivities.AddLocation;
import com.rrr.swift.R;

import java.util.ArrayList;

public class AddTask2 extends AppCompatActivity
{

    private static final String TAG = "AddTask2Activity";

    private ArrayList<String> mAddress = new ArrayList<>();

    private Button mSubmit;
    private EditText mNewTask;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    int maxID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_2);
        Log.d(TAG, "onCreate: started.");

        mSubmit = (Button) findViewById(R.id.add_task_btn);
        mNewTask = (EditText) findViewById(R.id.et_new_task);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Tasks");

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else
                {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }

        };

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    maxID = (int) dataSnapshot.getChildrenCount();
                }

                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is:" + value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(TAG, "Failed to read value", databaseError.toException());
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d(TAG, "onClick: Attempting to add object to database.");
                String newTask = mNewTask.getText().toString().trim();
                if(!newTask.equals(""))
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    //    String userID = user.getUid();  //causes crash if User Login Screen is bypassed

                    //Log.d(TAG,"maxID before increment is: " + maxID);
                    myRef.child(String.valueOf(maxID+1)).child("address").setValue(newTask);
                    toastMessage("Adding " + newTask + " to database...");

                    //reset the text
                    mNewTask.setText("");
                    maxID = maxID + 1;
                    Log.d(TAG,"maxID after increment is: " + maxID);

                    Intent myIntent = new Intent(AddTask2.this, AddEditLocationActivity.class);
                    AddTask2.this.startActivity(myIntent);
                }
            }
        });






    }




    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }


}
