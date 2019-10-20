package com.rrr.swift.SettingsActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rrr.swift.LocationActivities.AddEditLocationActivity;
import com.rrr.swift.R;
import com.rrr.swift.TaskActivities.AddTask1;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener
{

    Button addEditLocBtn, addEditTaskBtn, addEditInspectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addEditLocBtn = (Button) findViewById(R.id.add_edit_loc_btn);
        addEditTaskBtn = (Button) findViewById(R.id.add_edit_tsk_btn);
        addEditInspectBtn = (Button) findViewById(R.id.add_edit_insp_btn);

        addEditLocBtn.setOnClickListener(this);
        addEditTaskBtn.setOnClickListener(this);
        addEditInspectBtn.setOnClickListener(this);

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
                Intent myIntent2 = new Intent(SettingsActivity.this, AddTask1.class);
                SettingsActivity.this.startActivity(myIntent2);
                 break;

            case R.id.add_edit_insp_btn:
                Intent myIntent3 = new Intent(SettingsActivity.this, AddEditLocationActivity.class);
                SettingsActivity.this.startActivity(myIntent3);
                  break;
        }
    }

}
