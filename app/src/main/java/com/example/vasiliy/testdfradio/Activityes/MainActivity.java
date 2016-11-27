package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vasiliy.testdfradio.Fragments.AllRadioFragment;
import com.example.vasiliy.testdfradio.Adapters.ViewPagerAdapter;
import com.example.vasiliy.testdfradio.Fragments.FavoriteRadioFragment;
import com.example.vasiliy.testdfradio.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        initViews();
    }

    private void initViews() {
        (findViewById(R.id.ivMenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllRadioFragment(), getString(R.string.tab_all));
        adapter.addFragment(new FavoriteRadioFragment(), getString(R.string.tab_favorite));
        viewPager.setAdapter(adapter);
    }

}
