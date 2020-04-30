package com.rrr.swift.SplashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rrr.swift.AuthActivity.LoginActivity;
import com.rrr.swift.R;


public class SplashActivity extends Activity
{

        ImageView splashLogo;
        private static final String TAG = "SplashActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            Log.d(TAG, "onCreate: started.");
            splashLogo = findViewById(R.id.logo);

            Thread splashThread = new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashanimation);
                        splashLogo.startAnimation(rotateAnimation);
                        splashLogo.setVisibility(View.INVISIBLE);
                        sleep(4000);
                        Intent splashIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(splashIntent);
                        finish();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }


                }
            };
            splashThread.start();
        }
    }
