package com.rrr.swift.SplashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;


public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    ImageView splashLogo;

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
                Log.d(TAG, "run: started.");



                RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation1.setDuration(3000);
                rotateAnimation1.setInterpolator(getApplicationContext(), android.R.interpolator.accelerate_decelerate);
                rotateAnimation1.setAnimationListener(new Animation.AnimationListener()
                {
                    public void onAnimationStart(Animation animation)
                    {
                        Log.d(TAG, "onAnimationStart: started");
                    }

                    public void onAnimationEnd(Animation animation)
                    {
                        Log.d(TAG, "onAnimationEnd: started");
                    }

                    public void onAnimationRepeat(Animation animation) { }
                });

//                RotateAnimation rotateAnimation2 = new RotateAnimation(345, 355, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnimation2.setDuration(1000);
//                rotateAnimation2.setStartOffset(2000);
//                rotateAnimation2.setInterpolator(getApplicationContext(), android.R.interpolator.accelerate_decelerate);
//                rotateAnimation2.setAnimationListener(new Animation.AnimationListener()
//                {
//                    public void onAnimationStart(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationStart: started");
//                    }
//
//                    public void onAnimationEnd(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationEnd: started");
//                    }
//
//                    public void onAnimationRepeat(Animation animation) { }
//                });
//
//                RotateAnimation rotateAnimation3 = new RotateAnimation(355,365 , Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnimation3.setDuration(1000);
//                rotateAnimation3.setStartOffset(3000);
//                rotateAnimation3.setInterpolator(getApplicationContext(), android.R.interpolator.accelerate_decelerate);
//                rotateAnimation3.setAnimationListener(new Animation.AnimationListener()
//                {
//                    public void onAnimationStart(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationStart: started");
//                    }
//
//                    public void onAnimationEnd(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationEnd: started");
//                    }
//
//                    public void onAnimationRepeat(Animation animation) { }
//                });
//
//                RotateAnimation rotateAnimation4 = new RotateAnimation(365,360 , Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnimation3.setDuration(1000);
//                rotateAnimation3.setStartOffset(4000);
//                rotateAnimation3.setInterpolator(getApplicationContext(), android.R.interpolator.accelerate_decelerate);
//                rotateAnimation3.setAnimationListener(new Animation.AnimationListener()
//                {
//                    public void onAnimationStart(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationStart: started");
//                    }
//
//                    public void onAnimationEnd(Animation animation)
//                    {
//                        Log.d(TAG, "onAnimationEnd: started");
//                    }
//
//                    public void onAnimationRepeat(Animation animation) { }
//                });

                try
                {
                    int x,y;
                    x = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
                    y = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
                    Log.d(TAG, "Height Pixels: "+ x);
                    Log.d(TAG, "Width Pixels: "+ y);

                AnimationSet animationSet = new AnimationSet(true);
                animationSet.initialize(splashLogo.getMeasuredWidth(),splashLogo.getMeasuredHeight(),x,y);
                animationSet.addAnimation(rotateAnimation1);

//                animationSet.addAnimation(rotateAnimation2);
//                animationSet.addAnimation(rotateAnimation3);
//                animationSet.addAnimation(rotateAnimation4);
                splashLogo.setVisibility(View.INVISIBLE);

                splashLogo.startAnimation(animationSet);

                sleep(3500);
                Intent splashIntent = new Intent(getApplicationContext(), UserHomeActivity.class);
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
