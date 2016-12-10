package com.example.vasiliy.testdfradio.Activityes;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.Classes.Player;
import com.example.vasiliy.testdfradio.Classes.RadioState;
import com.example.vasiliy.testdfradio.DataClasses.Const;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.Interfaces.OnRadioListener;
import com.example.vasiliy.testdfradio.R;
import com.example.vasiliy.testdfradio.Services.NotificationService;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener, OnRadioListener {

    private final boolean DEBUG_PLAY_ACTIVITY = true; // true = debug on, false = debug off

    public static final String EXTRA_ID = "id_radio";

    private RelativeLayout mPlay;
    private RelativeLayout mPause;
    private ProgressBar mProgressBar;

    private ImageView mUnLike;
    private ImageView mLike;

    private TextView tvState;
    private TextView tvSubstate;

    private int mID;

    private RadioChannels mRadioChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mID = getIntent().getIntExtra(EXTRA_ID, 0);

        mRadioChannels = RadioChannels.getInstance();

        initializeUI();

        RadioState.addRadioListener(this);
        //mRadioManager.updateNotification("singer", "sonr", R.drawable.df_logo, R.drawable.df_logo);


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
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_for_share));
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
                pushSnack(getString(R.string.snack_like));
                RadioChannels.getInstance().saveLike(this, mID);
                break;
            case R.id.ivLike:
                debugToast("ivLike");
                setUnLike();
                pushSnack(getString(R.string.snack_unlike));
                RadioChannels.getInstance().saveDislike(this, mID);
                break;
            case R.id.rlPlay:
                //debugToast("ivPlay");
                showProgressBar();
                RadioChannels.getInstance().mPlayRadioWithId = mID;
                mRadioChannels.mMetaDataNameSongPlayingRadio = null;
                startRadio();
                Log.d("radio", mRadioChannels.mLinks[mRadioChannels.mIds.indexOf(mID)]);
                break;
            case R.id.rlPause:
                debugToast("ivPause");
                showPlay();
                //Player.stop();

                Intent pauseIntent = new Intent(this, NotificationService.class);
                pauseIntent.setAction(Const.ACTION.PLAY_ACTION);
                PendingIntent ppauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
                try {
                    ppauseIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

                //RadioChannels.getInstance().mPlayRadioWithId = -1;
                break;
        }
    }

    private void startRadio() {
        Intent serviceIntent = new Intent(PlayActivity.this, NotificationService.class);
        serviceIntent.setAction(Const.ACTION.STARTFOREGROUND_ACTION);
        startService(serviceIntent);
    }

    private void showPlay() {
        mPause.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPlay.setVisibility(View.VISIBLE);
        tvState.setText(getString(R.string.text_ready_to_play));
        tvSubstate.setText(getString(R.string.subtext_ready_to_play));
    }

    private void showProgressBar() {
        mPlay.setVisibility(View.INVISIBLE);
        mPause.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        tvState.setText(getString(R.string.text_connection));
        tvSubstate.setText(getString(R.string.subtext_connection));
    }

    private void showPause() {
        mPlay.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPause.setVisibility(View.VISIBLE);
        tvState.setText(mRadioChannels.mRadioNames[mRadioChannels.mIds.indexOf(mID)]);
        if (mRadioChannels.mMetaDataNameSongPlayingRadio != null) {
            tvSubstate.setText(mRadioChannels.mMetaDataNameSongPlayingRadio);
        } else {
            tvSubstate.setText(getString(R.string.subtext_play));
        }
    }

    private void setLike() {
        mLike.setVisibility(View.VISIBLE);
        mUnLike.setVisibility(View.INVISIBLE);
    }

    private void setUnLike() {
        mLike.setVisibility(View.INVISIBLE);
        mUnLike.setVisibility(View.VISIBLE);
    }

    private void pushSnack(String str) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.playCoordLayout),
                str,
                Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(getResources().getColor(R.color.snackLikeColor));
        ((TextView) snackView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.textColorSecondPrimary));
        snackbar.show();
    }

    private void initializeUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        tvState = (TextView) findViewById(R.id.tvState);
        tvState.setSelected(true);
        tvSubstate = (TextView) findViewById(R.id.tvSubstate);
        tvSubstate.setSelected(true);

        ((TextView) findViewById(R.id.tvTitle)).setText(mRadioChannels.mRadioNames[mRadioChannels.mIds.indexOf(mID)]);

        if (mRadioChannels.mLikes.contains(mID)) {
            setLike();
        } else {
            setUnLike();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRadioChannels.mPlayRadioWithId == mID) {
            if (RadioState.isPlaying()) {
                showPause();
            } else {
                showPlay();
            }
        } else {
            showPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RadioState.removeRadioListener(this);
    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPause();
                debugToast("onRadioStarted");
            }
        });
    }

    @Override
    public void onRadioPaused() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                debugToast("onRadioPaused");
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                debugToast("onRadioStopped");
            }
        });
    }

    @Override
    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressBar();
                debugToast("onRadioLoading");
            }
        });
    }

    @Override
    public void onRadioMetadata(final String s, final String s2) {
        //final String ss = s;
        //final String ss2 = s2;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                Log.d("MetaDataDebug", "s = " + s);
                Log.d("MetaDataDebug", "s2 = " + s2);
                Log.d("MetaDataDebug", "\n");
                if ("StreamTitle".equals(s)) {
                    tvSubstate.setText(s2);
                    //mRadioChannels.mMetaDataNameSongPlayingRadio = s2;
                }
            }
        });
    }

    @Override
    public void onRadioError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                //mRadioChannels.mPlayRadioWithId = -1;
                debugToast("onRadioError");
            }
        });
    }

    private void debugToast(String str) {
        if (DEBUG_PLAY_ACTIVITY) {
            Log.d("PlayActivity", str);
        }
    }


}
