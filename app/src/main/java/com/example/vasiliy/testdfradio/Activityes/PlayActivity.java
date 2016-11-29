package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;

import co.mobiwise.library.radio.RadioListener;
import co.mobiwise.library.radio.RadioManager;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, RadioListener {

    private final boolean DEBUG_PLAY_ACTIVITY = false; // true = debug on, false = debug off

    public static final String EXTRA_POSITION = "id_radio";

    private RelativeLayout mPlay;
    private RelativeLayout mPause;
    private ProgressBar mProgressBar;

    private ImageView mUnLike;
    private ImageView mLike;

    private int mID;

    RadioManager mRadioManager;

    private RadioChannels mRadioChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeUI();

        mRadioManager = RadioManager.with(this);
        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);
        //mRadioManager.updateNotification("singer", "sonr", R.drawable.df_logo, R.drawable.df_logo);

        mRadioChannels = RadioChannels.getInstance();

        mID = getIntent().getIntExtra(EXTRA_POSITION, 0);
        ((TextView) findViewById(R.id.tvTitle)).setText(mRadioChannels.mRadioNames[mID]);

        if (mRadioChannels.mLikes.contains(mID)) {
            setLike();
        } else {
            setUnLike();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rlBack:
                finish();
                break;
            case R.id.rlShare:
                debugToast("ivShare");
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_for_shape));
                startActivity(Intent.createChooser(intent, getString(R.string.text_description_action)));
                break;
            case R.id.ivYouTube:
                debugToast("ivYouTube");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.link_youtube)));
                startActivity(intent);
                break;
            case R.id.ivInstagram:
                debugToast("ivInstagram");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.link_instagram)));
                startActivity(intent);
                break;
            case R.id.ivVK:
                debugToast("ivVK");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.link_vk)));
                startActivity(intent);
                break;
            case R.id.ivUnLike:
                debugToast("ivUnLike");
                setLike();
                RadioChannels.getInstance().saveLike(this, mID);
                break;
            case R.id.ivLike:
                debugToast("ivLike");
                setUnLike();
                RadioChannels.getInstance().saveDislike(this, mID);
                break;
            case R.id.rlPlay:
                debugToast("ivPlay");
                showProgressBar();
                RadioChannels.getInstance().mPlayRadioWithId = mID;
                mRadioManager.startRadio(mRadioChannels.mLinks[mRadioChannels.mIds.indexOf(mID)]);
                Log.d("radio", mRadioChannels.mLinks[mRadioChannels.mIds.indexOf(mID)]);
                break;
            case R.id.rlPause:
                debugToast("ivPause");
                showPlay();
                RadioChannels.getInstance().mPlayRadioWithId = -1;
                mRadioManager.stopRadio();
                break;
        }
    }

    private void showPlay() {
        mPause.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPlay.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mPlay.setVisibility(View.INVISIBLE);
        mPause.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showPause() {
        mPlay.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPause.setVisibility(View.VISIBLE);
    }

    private void setLike() {
        mLike.setVisibility(View.VISIBLE);
        mUnLike.setVisibility(View.INVISIBLE);
    }

    private void setUnLike() {
        mLike.setVisibility(View.INVISIBLE);
        mUnLike.setVisibility(View.VISIBLE);
    }

    private void initializeUI() {
        mPlay = (RelativeLayout) findViewById(R.id.rlPlay);
        mPlay.setVisibility(View.VISIBLE);
        mPlay.setOnClickListener(this);

        mPause = (RelativeLayout) findViewById(R.id.rlPause);
        mPause.setVisibility(View.INVISIBLE);
        mPause.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        mProgressBar.setVisibility(View.INVISIBLE);

        findViewById(R.id.rlBack).setOnClickListener(this);
        findViewById(R.id.rlShare).setOnClickListener(this);
        findViewById(R.id.ivYouTube).setOnClickListener(this);
        findViewById(R.id.ivInstagram).setOnClickListener(this);
        findViewById(R.id.ivVK).setOnClickListener(this);

        mUnLike = (ImageView) findViewById(R.id.ivUnLike);
        mUnLike.setOnClickListener(this);
        mUnLike.setVisibility(View.VISIBLE);

        mLike = (ImageView) findViewById(R.id.ivLike);
        mLike.setOnClickListener(this);
        mLike.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRadioManager.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mRadioChannels.mPlayRadioWithId == mID){
            showPause();
        } else {
            showPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRadioManager.disconnect();
    }

    @Override
    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                //Toast.makeText(getApplicationContext(), "onRadioLoading", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRadioConnected() {
        mRadioManager.updateNotificationSmallImage(R.drawable.df_logo);
        mRadioManager.updateNotificationImage(R.drawable.df_logo);
        //mRadioManager.updateNotificationImage(BitmapFactory.decodeResource(getResources(), R.drawable.df_logo));
    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                //Toast.makeText(getApplicationContext(), "onRadioStarted", Toast.LENGTH_SHORT).show();
                showPause();
                RadioChannels.getInstance().mPlayRadioWithId = mID;
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                //Toast.makeText(getApplicationContext(), "onRadioStopped", Toast.LENGTH_SHORT).show();
                showPlay();
                RadioChannels.getInstance().mPlayRadioWithId = -1;
            }
        });

    }

    @Override
    public void onMetaDataReceived(String s, String s2) {

    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                //Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
                showPlay();
                RadioChannels.getInstance().mPlayRadioWithId = -1;
            }
        });
    }

    private void debugToast(String str) {
        if (DEBUG_PLAY_ACTIVITY) {
            Toast.makeText(PlayActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }


}
