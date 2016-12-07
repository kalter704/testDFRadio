package com.example.vasiliy.testdfradio.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.vasiliy.testdfradio.Activityes.PlayActivity;
import com.example.vasiliy.testdfradio.Classes.Player;
import com.example.vasiliy.testdfradio.Classes.RadioState;
import com.example.vasiliy.testdfradio.DataClasses.Const;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;

public class NotificationService extends Service {
    /*
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */

    public static Context context;
    Notification notification;
    boolean isPause = true;
    RadioChannels radioChannels = RadioChannels.getInstance();

    private void showNotification(int pos) {

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification);

        // надод попроваить!!!!!!!!11
        Intent notificationIntent = new Intent(this, PlayActivity.class);
        //notificationIntent.setAction(Const.ACTION.MAIN_ACTION);
        notificationIntent.putExtra(PlayActivity.EXTRA_POSITION, radioChannels.mPlayRadioWithId);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //views.setOnClickPendingIntent(R.id.notification_base, pendingIntent);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Const.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Const.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);

        views.setOnClickPendingIntent(R.id.notification_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.notification_collapse, pcloseIntent);

        views.setTextViewText(R.id.notification_line_one, radioChannels.mRadioNames[radioChannels.mIds.indexOf(radioChannels.mPlayRadioWithId)]);

        if (pos == 0) {
            views.setImageViewResource(R.id.notification_play, R.drawable.ic_pause);
            views.setTextViewText(R.id.notification_line_two, radioChannels.mMetaDataNameSongPlayingRadio != null ? radioChannels.mMetaDataNameSongPlayingRadio : getString(R.string.subtext_play));
        }
        if (pos == 1) {
            views.setImageViewResource(R.id.notification_play, R.drawable.ic_pause);
            views.setTextViewText(R.id.notification_line_two, radioChannels.mMetaDataNameSongPlayingRadio != null ? radioChannels.mMetaDataNameSongPlayingRadio : getString(R.string.subtext_play));
        }
        if (pos == 2) {
            views.setImageViewResource(R.id.notification_play, R.drawable.ic_play_arrow);
            views.setTextViewText(R.id.notification_line_two, getString(R.string.subtext_ready_to_play));
        }
        notification = new Notification.Builder(this).build();
        notification.contentView = views;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.icon = R.drawable.df_logo;
        notification.contentIntent = pendingIntent;
        startForeground(Const.FOREGROUND_SERVICE, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        RadioChannels radioChannels = RadioChannels.getInstance();

        context = this;
        if (intent.getAction().equals(Const.ACTION.STARTFOREGROUND_ACTION)) {
            isPause = false;
            showNotification(0);
            Player.start(this, radioChannels.mLinks[radioChannels.mIds.indexOf(radioChannels.mPlayRadioWithId)]);
        } else if (intent.getAction().equals(Const.ACTION.PLAY_ACTION)) {
            if (!isPause) {
                showNotification(2);
                Player.stop();
                isPause = true;
            } else {
                showNotification(1);
                isPause = false;
                Player.start(this, radioChannels.mLinks[radioChannels.mIds.indexOf(radioChannels.mPlayRadioWithId)]);
            }
        } else if (intent.getAction().equals(Const.ACTION.STOPFOREGROUND_ACTION)) {
            Player.stop();
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Const.ACTION.UPDATE_NOTIFICATION_ACTION)) {
            showNotification(0);
        }

        return START_STICKY;
    }

}
