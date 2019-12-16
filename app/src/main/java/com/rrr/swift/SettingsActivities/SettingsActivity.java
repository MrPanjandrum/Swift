package com.rrr.swift.SettingsActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.AddTask1Activity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "SettingsActivity";

    Button addEditLocBtn, addEditTaskBtn, addEditInspectBtn, addReportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "onCreate: started.");

        addEditLocBtn = (Button) findViewById(R.id.add_edit_loc_btn);
        addEditTaskBtn = (Button) findViewById(R.id.add_edit_tsk_btn);
        addEditInspectBtn = (Button) findViewById(R.id.add_edit_insp_btn);
        addReportBtn = (Button) findViewById(R.id.add_report_btn);

        addEditLocBtn.setOnClickListener(this);
        addEditTaskBtn.setOnClickListener(this);
        addEditInspectBtn.setOnClickListener(this);
        addReportBtn.setOnClickListener(this);

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

            case R.id.add_edit_insp_btn:
                toastMessage("Add Inpspection Clicked");
                  break;

            case R.id.add_report_btn:
                toastMessage("Add Report Clicked");
                break;
        }
    }



    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

}
