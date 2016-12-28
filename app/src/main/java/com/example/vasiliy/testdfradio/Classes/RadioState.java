package com.example.vasiliy.testdfradio.Classes;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.vasiliy.testdfradio.DataClasses.Const;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.Interfaces.OnRadioListener;
import com.example.vasiliy.testdfradio.Services.NotificationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasiliy on 05.12.16.
 */

public class RadioState {

    public enum State {
        PLAY,
        PAUSE,
        STOP,
        LOADING,
        INTERRUPTED
    }

    public static boolean isTransientCanDuck = false;

    public static Context context = null;

    public static State state = State.STOP;

    public static List<OnRadioListener> listeners = new ArrayList<>();

    public static boolean isPlaying() {
        return state == State.PLAY;
    }

    public static void addRadioListener(OnRadioListener listener) {
        listeners.add(listener);
    }

    public static void removeRadioListener(OnRadioListener listener) {
        listeners.remove(listener);
    }


    public static void notifRadioStarted() {
        //state = State.PLAY;
        for (OnRadioListener listener : listeners) {
            listener.onRadioStarted();
        }
    }

    public static void notifRadioPaused() {
        //state = State.PAUSE;
        for (OnRadioListener listener : listeners) {
            listener.onRadioPaused();
        }
    }

    public static void notifRadioStopped() {
        if (state != State.LOADING) {
            for (OnRadioListener listener : listeners) {
                listener.onRadioStopped();
            }
        }
    }

    public static void notifRadioLoading() {
        //state = State.PLAY;
        for (OnRadioListener listener : listeners) {
            listener.onRadioLoading();
        }
    }

    public static void notifRadioError() {
        //state = State.STOP;
        for (OnRadioListener listener : listeners) {
            listener.onRadioError();
        }
    }

    public static void notifRadioMetadata(String s, String s2) {
        if ("StreamTitle".equals(s)) {
            RadioChannels.getInstance().mMetaDataNameSongPlayingRadio = s2;

            Intent updateIntent = new Intent(context, NotificationService.class);
            updateIntent.setAction(Const.ACTION.UPDATE_NOTIFICATION_ACTION);
            PendingIntent pupdateIntent = PendingIntent.getService(context, 0, updateIntent, 0);
            try {
                pupdateIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }

            for (OnRadioListener listener : listeners) {
                listener.onRadioMetadata(s, s2);
            }
        }
    }

    public static boolean hasConnectionToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
