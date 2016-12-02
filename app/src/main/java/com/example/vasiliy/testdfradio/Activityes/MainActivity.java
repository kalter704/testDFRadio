package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.Fragments.AllRadioFragment;
import com.example.vasiliy.testdfradio.Adapters.ViewPagerAdapter;
import com.example.vasiliy.testdfradio.Fragments.FavoriteRadioFragment;
import com.example.vasiliy.testdfradio.R;

import co.mobiwise.library.radio.RadioListener;
import co.mobiwise.library.radio.RadioManager;


public class MainActivity extends AppCompatActivity {

    private RadioManager mRadioManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RadioChannels.getInstance().mLikes.clear();
        //RadioChannels.getInstance().mLikes.add(0);
        //RadioChannels.getInstance().mLikes.add(2);
        //RadioChannels.getInstance().mPlayRadioWithId = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        initializeUI();

        mRadioManager = RadioManager.with(this);

        //if(mRadioManager.getService() == null) {
        mRadioManager.connect();
        //}
    }

    private void initializeUI() {
        (findViewById(R.id.rlMenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllRadioFragment(), getString(R.string.tab_all));
        adapter.addFragment(new FavoriteRadioFragment(), getString(R.string.tab_favorite));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        mRadioManager.disconnect();
    }



}
