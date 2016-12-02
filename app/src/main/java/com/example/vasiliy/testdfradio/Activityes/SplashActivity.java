package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;

public class SplashActivity extends AppCompatActivity {

    private int timeForSplashAtivity = 1;
    //private int timeForSplashAtivity = 5;
    private int iterationTime = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RadioChannels.getInstance().loadLikes(this);

        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    int splashTimer = 0;
                    while(splashTimer < (timeForSplashAtivity * 1000)) {
                        sleep(iterationTime);
                        splashTimer += iterationTime;
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
