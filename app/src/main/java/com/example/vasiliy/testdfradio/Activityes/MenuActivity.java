package com.example.vasiliy.testdfradio.Activityes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        initAll();
    }

    private void initAll() {
        (findViewById(R.id.ivBack)).setOnClickListener(this);
        (findViewById(R.id.llShare)).setOnClickListener(this);
        (findViewById(R.id.llInfo)).setOnClickListener(this);
        (findViewById(R.id.llStar)).setOnClickListener(this);
        (findViewById(R.id.llMoney)).setOnClickListener(this);
        (findViewById(R.id.llEmail)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                this.finish();
                break;
            case R.id.llShare:
                Toast.makeText(MenuActivity.this, String.valueOf("llShare"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.llInfo:
                Toast.makeText(MenuActivity.this, String.valueOf("llInfo"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.llStar:
                Toast.makeText(MenuActivity.this, String.valueOf("llStar"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.llMoney:
                Toast.makeText(MenuActivity.this, String.valueOf("llMoney"), Toast.LENGTH_SHORT).show();
                break;
            case R.id.llEmail:
                Toast.makeText(MenuActivity.this, String.valueOf("llEmail"), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
