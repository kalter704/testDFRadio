package com.example.vasiliy.testdfradio.Classes;

import android.content.Context;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.PlayerCallback;

import static android.content.Context.TELEPHONY_SERVICE;


public class Player implements PlayerCallback {

    private final int AUDIO_BUFFER_CAPACITY_MS = 800;
    private final int AUDIO_DECODE_CAPACITY_MS = 400;

    private final String SUFFIX_PLS = ".pls";
    private final String SUFFIX_RAM = ".ram";
    private final String SUFFIX_WAX = ".wax";

    private MultiPlayer mRadioPlayer;

    private static Player instance = new Player();

    private Context context = null;

    private Player() {}

    public static void start(Context context, String URL) {
        instance.context = context;
        if (RadioState.isPlaying()) {
            instance.getPlayer().stop();
        }
        //RadioState.notifRadioLoading();
        RadioState.state = RadioState.State.LOADING;
        if (instance.checkSuffix(URL)) {
            instance.decodeStremLink(URL);
        } else {
            instance.getPlayer().playAsync(URL);
        }
    }

    public static void stop() {
        instance.getPlayer().stop();
    }

    private boolean checkSuffix(String streamUrl) {
        if (streamUrl.contains(SUFFIX_PLS) ||
                streamUrl.contains(SUFFIX_RAM) ||
                streamUrl.contains(SUFFIX_WAX))
            return true;
        else
            return false;
    }

    private void decodeStremLink(String streamLink) {
        new StreamLinkDecoder(streamLink) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                start(context, s);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void playerStarted() {
        RadioState.notifRadioStarted();
    }

    @Override
    public void playerStopped(int i) {
        RadioState.notifRadioStopped();
    }

    @Override
    public void playerMetadata(String s, String s1) {
        RadioState.notifRadioMetadata(s, s1);
    }

    @Override
    public void playerException(Throwable throwable) {
        RadioState.notifRadioError();
        throwable.printStackTrace();
    }

    @Override
    public void playerAudioTrackCreated(AudioTrack audioTrack) {

    }

    @Override
    public void playerPCMFeedBuffer(boolean b, int i, int i1) {

    }

    private MultiPlayer getPlayer() {
        try {
            java.net.URL.setURLStreamHandlerFactory(new java.net.URLStreamHandlerFactory() {
                public java.net.URLStreamHandler createURLStreamHandler(String protocol) {
                    Log.d("LOG", "Asking for stream handler for protocol: '" + protocol + "'");
                    if ("icy".equals(protocol))
                        return new com.spoledge.aacdecoder.IcyURLStreamHandler();
                    return null;
                }
            });
        } catch (Throwable t) {
            Log.w("LOG", "Cannot set the ICY URLStreamHandler - maybe already set ? - " + t);
        }

        if (mRadioPlayer == null) {
            mRadioPlayer = new MultiPlayer(this, AUDIO_BUFFER_CAPACITY_MS, AUDIO_DECODE_CAPACITY_MS);
            mRadioPlayer.setResponseCodeCheckEnabled(false);
            mRadioPlayer.setPlayerCallback(this);
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (mTelephonyManager != null)
                mTelephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        return mRadioPlayer;
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {

                /**
                 * Stop radio and set interrupted if it is playing on incoming call.
                 */
                if (RadioState.isPlaying()) {
                    RadioState.state = RadioState.State.INTERRUPTED;
                    stop();
                }

            } else if (state == TelephonyManager.CALL_STATE_IDLE) {

                /**
                 * Keep playing if it is interrupted.
                 */
                if (RadioState.state == RadioState.State.INTERRUPTED) {
                    RadioChannels radioChannels = RadioChannels.getInstance();
                    start(context, radioChannels.mLinks[radioChannels.mIds.indexOf(radioChannels.mPlayRadioWithId)]);
                }

            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                /**
                 * Stop radio and set interrupted if it is playing on outgoing call.
                 */
                if (RadioState.isPlaying()) {
                    RadioState.state = RadioState.State.INTERRUPTED;
                    stop();
                }

            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

}


/*
public class Player {


    private Player() {
    }

    public static void start(String URL) {

    }

    public static void stop() {
    }

    public boolean checkSuffix(String streamUrl) {
        return true;
    }

    private void decodeStremLink(String streamLink) {

    }

    private boolean getPlayer() {
        return true;
    }


}
*/