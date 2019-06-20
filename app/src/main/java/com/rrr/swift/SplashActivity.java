package com.rrr.swift;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

        ImageView splashLogo;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
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
                        sleep(3000);
                        Intent splashIntent = new Intent(getApplicationContext(),MainActivity.class);
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
