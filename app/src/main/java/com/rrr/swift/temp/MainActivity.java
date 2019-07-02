package com.rrr.swift.temp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rrr.swift.temp2.Location;
import com.rrr.swift.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

DatabaseReference reference;
ArrayList<Location> list;
RecyclerView recyclerView;
SearchView searchView;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference("Locations");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart()
    {
        super.onStart();
        if(reference != null)
        {
            reference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.exists())
                    {
                        list = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            list.add(ds.getValue(Location.class));
                        }
                        AdapterClass adapterClass = new AdapterClass(list);
                        recyclerView.setAdapter(adapterClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void search(String str)
    {
        ArrayList<Location> myList = new ArrayList<>();
        for(Location object : list)
        {
            if(object.getAddress().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




}
