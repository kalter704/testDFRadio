package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.Activityes.InfoActivityes.AboutDFActivity;
import com.example.vasiliy.testdfradio.Activityes.InfoActivityes.DevWriteActivity;
import com.example.vasiliy.testdfradio.Activityes.InfoActivityes.DonateActivity;
import com.example.vasiliy.testdfradio.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private final boolean DEBUG_MENU_ACTIVITY = false; // true = debug on, false = debug off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeUI();
    }

    private void initializeUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.tvVersion)).setText("Версия: Alpha");
        /*
        try {
            ((TextView)findViewById(R.id.tvVersion)).setText("Версия: " + String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        */

        (findViewById(R.id.rlBack)).setOnClickListener(this);
        (findViewById(R.id.llShare)).setOnClickListener(this);
        (findViewById(R.id.llInfo)).setOnClickListener(this);
        (findViewById(R.id.llStar)).setOnClickListener(this);
        (findViewById(R.id.llMoney)).setOnClickListener(this);
        (findViewById(R.id.llEmail)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.llShare:
                debugToast("llShare");
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_for_share));
                startActivity(Intent.createChooser(intent, getString(R.string.text_description_action)));
                break;
            case R.id.llInfo:
                debugToast("llInfo");
                startActivity(new Intent(getApplicationContext(), AboutDFActivity.class));
                break;
            case R.id.llStar:
                debugToast("llStar");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.link_to_app_in_google_play)));
                startActivity(intent);
                break;
            case R.id.llMoney:
                debugToast("llMoney");
                startActivity(new Intent(getApplicationContext(), DonateActivity.class));
                break;
            case R.id.llEmail:
                debugToast("llEmail");
                startActivity(new Intent(getApplicationContext(), DevWriteActivity.class));
                break;
        }
    }

    private void debugToast(String str) {
        if(DEBUG_MENU_ACTIVITY) {
            Toast.makeText(MenuActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}
