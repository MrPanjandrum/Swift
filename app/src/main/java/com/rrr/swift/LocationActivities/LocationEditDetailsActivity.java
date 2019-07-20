package com.rrr.swift.LocationActivities;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.R;

import java.util.ArrayList;

public class LocationEditDetailsActivity extends Activity
{

    private static final String TAG = "LocationEditDetailsActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Locations");

    EditText mAddress, mAptNum, mCity, mState, mZip;


//    Spinner mSpinner;
//    String[] mNumbers = {"Select Number of Dwellings","1 - Dwelling","2 - Dwellings","3","4","5","6","7","8","9","10"};
//
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_edit_details);

        mAddress = (EditText)findViewById(R.id.et_address);
        mAptNum = (EditText)findViewById(R.id.et_apt_num);
        mCity = (EditText)findViewById(R.id.et_city);
        mState = (EditText)findViewById(R.id.et_state);
        mZip = (EditText)findViewById(R.id.et_zip);

//        mSpinner = findViewById(R.id.spinner);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mNumbers);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//            }
//        });
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                showData(dataSnapshot);
            }



            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot)
    {

    for (DataSnapshot ds : dataSnapshot.getChildren())
    {
//        Location location = new Location();
//        location.setAddress(ds.child(address).getValue(Location.class).getAddress());
//
//        ArrayList<String> array = new ArrayList<>();
//        array.add(location.getAddress());
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
//        mListView.setAdapter(adapter);

    }

    }


}
