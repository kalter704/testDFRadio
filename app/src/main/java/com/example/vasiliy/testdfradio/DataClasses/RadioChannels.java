package com.example.vasiliy.testdfradio.DataClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.vasiliy.testdfradio.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vasiliy on 27.11.16.
 */

public class RadioChannels {

    private static RadioChannels ourInstance = new RadioChannels();

    public static RadioChannels getInstance() {
        return ourInstance;
    }

    private RadioChannels() {
    }

/*
    public int mIds[] = {
            0,
            1,
            2,
            3
    };
    */

    public List<Integer> mIds = new ArrayList<Integer>() {{
        add(0);
        add(1);
        add(2);
        add(3);
    }};

    public String mRadioNames[] = {
            "CRAZY DREAM1",
            "CRAZY DREAM2",
            "CRAZY DREAM3",
            "CRAZY DREAM4"
    };

    public String mLocations[] = {
            "Moscow, Russia",
            "Piter, Russia",
            "Samara, Russia",
            "Rostov, Russia"
    };

    /*
    public String mLinks[] = {
            "http://streaming.shoutcast.com/80sPlanet?lang=ru-RU%2cru%3bq%3d0.8%2cen-US%3bq%3d0.6%2cen%3bq%3d0.4",
            "http://streaming.streamonomy.com/keepfree60s",
            "http://streaming.shoutcast.com/80sPlanet?lang=ru-RU%2cru%3bq%3d0.8%2cen-US%3bq%3d0.6%2cen%3bq%3d0.4",
            "http://streaming.streamonomy.com/keepfree60s"
    };
    */

    public String mLinks[] = {
            "http://proxy.sidedark-warez.pl/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://leather-bg.com/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://prx.afkcz.eu/prx/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://efintrust.com/muro/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D"
    };

    // Указать индекс (i - 1)
    public List<Integer> mLikes = new ArrayList<>();

    public int mPlayRadioWithId = -1;

    public void saveLike(Context context, int n) {
        if(!mLikes.contains(n)) {
            mLikes.add(n);
            Collections.sort(mLikes);
        }
        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(String.valueOf(n), "like");
        ed.apply();
    }

    public void saveDislike(Context context, int n) {
        if(mLikes.contains(n)) {
            mLikes.remove(Integer.valueOf(n));
        }
        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString(String.valueOf(n), "dislike");
        ed.apply();
    }

    public void loadLikes(Context context) {
        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
        mLikes.clear();
        for(int i = 0; i < mIds.size(); i++) {
            if(sPref.getString(String.valueOf(mIds.get(i)), "").equals("like")) {
                mLikes.add(i);
            }
        }
    }

}

/*
    public int mIds[] = {
            0,
            1,
            2,
            3,
            1,
            2,
            3,
            1,
            2,
            3,
            1,
            2,
            3
    };

    public String mRadioNames[] = {
            "CRAZY DREAM1",
            "CRAZY DREAM2",
            "CRAZY DREAM3",
            "CRAZY DREAM4",
            "CRAZY DREAM2",
            "CRAZY DREAM3",
            "CRAZY DREAM4",
            "CRAZY DREAM2",
            "CRAZY DREAM3",
            "CRAZY DREAM4",
            "CRAZY DREAM2",
            "CRAZY DREAM3",
            "CRAZY DREAM4"
    };

    public String mLocations[] = {
            "Moscow, Russia",
            "Piter, Russia",
            "Samara, Russia",
            "Rostov, Russia",
            "Piter, Russia",
            "Samara, Russia",
            "Rostov, Russia",
            "Piter, Russia",
            "Samara, Russia",
            "Rostov, Russia",
            "Piter, Russia",
            "Samara, Russia",
            "Rostov, Russia"
    };

    public String mLinks[] = {
            "http://proxy.sidedark-warez.pl/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://leather-bg.com/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://prx.afkcz.eu/prx/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://efintrust.com/muro/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D"
    };

    // Указать индекс (i - 1)
    public List<Integer> mLikes = new ArrayList<>();

    public int mPlayRadioWithId = -1;
}
*/