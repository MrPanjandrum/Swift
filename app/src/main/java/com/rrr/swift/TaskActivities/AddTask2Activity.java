package com.rrr.swift.TaskActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTask2Activity extends AppCompatActivity
{

    private static final String TAG = "AddTask2Activity";

    private ArrayList<String> mAddress = new ArrayList<>();

    private Button mSubmit;
    private EditText mTaskName, mTaskArea, mTaskDescription;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private TextView taskLocation;
    private String defaultTaskStatus = "stopped";

    int maxID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_2);
        Log.d(TAG, "onCreate: started.");

        mSubmit = (Button) findViewById(R.id.add_task_btn);
        mTaskName = (EditText) findViewById(R.id.et_task_name);
        mTaskArea = (EditText) findViewById(R.id.et_task_area);
        mTaskDescription = (EditText)findViewById(R.id.et_task_description);

        
        taskLocation = (TextView) findViewById(R.id.tv_selected_location);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Tasks");


        getIncomingIntent();

        taskLocation.setText(mAddress.get(0));  //sets textview to selected task location



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
                    toastMessage("Signed-in with: " + user.getEmail());
                } else
                {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Signed-out. Must sign-in.");
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

                String taskName = mTaskName.getText().toString().trim();
                String taskArea = mTaskArea.getText().toString().trim();
                String taskDescription = mTaskDescription.getText().toString().trim();


                long epoch = System.currentTimeMillis()/1000;       //gets current epoch time


                if(!taskName.equals(""))
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();  //causes crash if User Login Screen is bypassed

                    //Log.d(TAG,"maxID before increment is: " + maxID);
                    myRef.child(String.valueOf(maxID+1)).child("address").setValue(mAddress.get(0));    //sets values to firebase rtb
                    myRef.child(String.valueOf(maxID+1)).child("taskName").setValue(taskName);
                    myRef.child(String.valueOf(maxID+1)).child("taskArea").setValue(taskArea);
                    myRef.child(String.valueOf(maxID+1)).child("taskDescription").setValue(taskDescription);
                    myRef.child(String.valueOf(maxID+1)).child("taskStatus").setValue(defaultTaskStatus);
                    myRef.child(String.valueOf(maxID+1)).child("dateTest").setValue(epoch);
                    myRef.child(String.valueOf(maxID+1)).child("taskNum").setValue(maxID+1);

                    toastMessage("Adding " + taskName + " to database...");

                    //reset the text
                    mTaskName.setText("");
                    maxID = maxID + 1;
                    Log.d(TAG,"maxID after increment is: " + maxID);

                    Intent myIntent = new Intent(AddTask2Activity.this, AddTask1Activity.class);
                    AddTask2Activity.this.startActivity(myIntent);
                }
            }
        });


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("address"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            mAddress = getIntent().getStringArrayListExtra("address");

            Intent intent = new Intent(this, AddTask2Activity.class);
            intent.putExtra("address",mAddress);

        }
    }

    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }


}
