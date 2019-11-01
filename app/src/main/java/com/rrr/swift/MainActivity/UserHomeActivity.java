package com.rrr.swift.MainActivity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rrr.swift.HomeActivity;
import com.rrr.swift.LocationActivities.LocationActivity;
import com.rrr.swift.R;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        //TO DO: Look in to Toolbar issue
       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, /*toolbar,*/ R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_menu)   //handles navigation menu click
        {
            Intent myIntent = new Intent(UserHomeActivity.this, HomeActivity.class);
            UserHomeActivity.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_locations)
        {
            Intent myIntent = new Intent(UserHomeActivity.this, LocationActivity.class);
            UserHomeActivity.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_work_orders)
        {
            Intent myIntent = new Intent(UserHomeActivity.this, LocationActivity.class);
            UserHomeActivity.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_settings)
        {
            Intent myIntent = new Intent(UserHomeActivity.this, LocationActivity.class);
            UserHomeActivity.this.startActivity(myIntent);
        }
        else if (id == R.id.nav_share)
        {
            Toast toast=Toast.makeText(getApplicationContext(),"Clicked: Share", Toast.LENGTH_SHORT);toast.show();
        }
        else if (id == R.id.nav_send)
        {
            Toast toast=Toast.makeText(getApplicationContext(),"Clicked: Send", Toast.LENGTH_SHORT);toast.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void openLocationActivity(View view)
    {
        Intent myIntent = new Intent(UserHomeActivity.this, LocationActivity.class);
        UserHomeActivity.this.startActivity(myIntent);
    }




}
