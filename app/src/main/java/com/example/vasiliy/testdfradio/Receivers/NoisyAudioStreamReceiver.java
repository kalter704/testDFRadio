package com.example.vasiliy.testdfradio.Receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.example.vasiliy.testdfradio.Classes.RadioState;
import com.example.vasiliy.testdfradio.DataClasses.Const;
import com.example.vasiliy.testdfradio.Services.NotificationService;

public class NoisyAudioStreamReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            if ((RadioState.state == RadioState.State.LOADING) || RadioState.state == RadioState.State.PLAY) {
                Intent pauseIntent = new Intent(context, NotificationService.class);
                pauseIntent.setAction(Const.ACTION.PLAY_ACTION);
                PendingIntent ppauseIntent = PendingIntent.getService(context, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    ppauseIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            } else if (RadioState.state == RadioState.State.INTERRUPTED) {
                RadioState.state = RadioState.State.STOP;
            }
        }
    }
}
