package com.rrr.swift.SettingsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.AddTask1Activity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "SettingsActivity";

    Button addEditLocBtn, addEditTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: started.");

        addEditLocBtn = findViewById(R.id.add_edit_loc_btn);
        addEditTaskBtn = findViewById(R.id.add_edit_tsk_btn);

        addEditLocBtn.setOnClickListener(this);
        addEditTaskBtn.setOnClickListener(this);

//        TextView tv1 = findViewById(R.id.icons8_link);

//        tv1.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_edit_loc_btn:
                Intent myIntent = new Intent(SettingsActivity.this, AddEditLocationActivity.class);
                SettingsActivity.this.startActivity(myIntent);
                break;

            case R.id.add_edit_tsk_btn:
                Intent myIntent2 = new Intent(SettingsActivity.this, AddTask1Activity.class);
                SettingsActivity.this.startActivity(myIntent2);
                 break;

        }
    }

    public void openHomeActivity(View view)
    { Intent homeIntent = new Intent(getApplicationContext(), UserHomeActivity.class);
    startActivity(homeIntent);
    }

    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }


}
