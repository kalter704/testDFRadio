package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasiliy.testdfradio.Classes.RadioState;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;

public class SplashActivity extends AppCompatActivity {

    private int mSecForSplashActivity = 1;
    private int mIterationTime = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RadioState.context = this;

        RadioChannels.getInstance().loadLikes(this);

        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    int splashTimer = 0;
                    while(splashTimer < (mSecForSplashActivity * 1000)) {
                        sleep(mIterationTime);
                        splashTimer += mIterationTime;
                    }
                    startActivity(new Intent(SplashActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }
        };
        splashTimer.start();
    }
}
