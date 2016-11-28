package com.example.vasiliy.testdfradio.Activityes;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{

    private final boolean DEBUG_PLAY_ACTIVITY = false; // true = debug on, false = debug off

    public static final String EXTRA_POSITION = "id_radio";

    private RelativeLayout mPlay;
    private RelativeLayout mPause;
    private ProgressBar mProgressBar;

    private ImageView mUnLike;
    private ImageView mLike;

    private int mID;

    private RadioChannels radioChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        radioChannels = RadioChannels.getInstance();

        mID = getIntent().getIntExtra(EXTRA_POSITION, 0);
        ((TextView) findViewById(R.id.tvTitle)).setText(radioChannels.mRadioNames[mID]);

        if(radioChannels.mLikes.contains(mID)) {
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
                play();
                RadioChannels.getInstance().mPlayRadioWithId = mID;
                break;
            case R.id.rlPause:
                debugToast("ivPause");
                pause();
                RadioChannels.getInstance().mPlayRadioWithId = -1;
                break;
        }
    }

    private void play() {
        mPlay.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        // TODO: дописать включение радио
    }

    private void pause() {
        mPause.setVisibility(View.INVISIBLE);
        mPlay.setVisibility(View.VISIBLE);
        // TODO: дописать выключение радио
    }

    private void setLike() {
        mLike.setVisibility(View.VISIBLE);
        mUnLike.setVisibility(View.INVISIBLE);
        // TODO: дописать логику сохранения лайка
    }

    private void setUnLike() {
        mLike.setVisibility(View.INVISIBLE);
        mUnLike.setVisibility(View.VISIBLE);
        // TODO: дописать логику сохранения дизлайка
    }

    private void initViews() {
        mPlay = (RelativeLayout) findViewById(R.id.rlPlay);
        mPlay.setVisibility(View.INVISIBLE);
        mPlay.setOnClickListener(this);

        mPause = (RelativeLayout) findViewById(R.id.rlPause);
        mPause.setVisibility(View.VISIBLE);
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

    private void debugToast(String str) {
        if(DEBUG_PLAY_ACTIVITY) {
            Toast.makeText(PlayActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}
