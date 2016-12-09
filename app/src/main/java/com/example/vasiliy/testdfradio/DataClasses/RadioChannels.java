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

    private static RadioChannels mInstance = new RadioChannels();

    public static RadioChannels getInstance() {
        return mInstance;
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
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
        add(10);
        add(11);

        add(12);
        add(13);
        add(14);
        add(15);
        add(16);
        add(17);
        add(18);
    }};

    public String mRadioNames[] = {
            // 0
            "CRAZY DREAM1",
            // 1
            "CRAZY DREAM2",
            // 2
            "CRAZY DREAM3",
            // 4
            "Русский хит",
            // 5
            "Dace Wave",
            // 6
            "SCORES OF MUSIC FROM MOVIES",
            // 7
            "Royal Russian",
            // 8
            "PAL STATION",
            // 9
            "RETRO HIT",
            // 10
            "STAR FM Maximum Rock Berlin",
            // 11
            "DODO FM",

            // 12
            "Радио Like FM",
            // 13
            "Радио Ретро FM",
            // 14
            "Радио Jazz",
            // 15
            "Радио Rock FM",
            // 16
            "Радио классической музыки \"Орфей\"",
            // 17
            "Радио Монте-Карло",
            // 18
            "Радио Книга"
    };

    public String mLocations[] = {
            // 0
            "Moscow, Russia",
            // 1
            "Piter, Russia",
            // 2
            "Samara, Russia",
            // 4
            "Типа Россия",
            // 5
            "ХЗ откуда",
            // 6
            "Melti",
            // 7
            "Tambovv, Russia",
            // 8
            "Palestina",
            // 9
            "Russia",
            // 10
            "Berlin",
            // 11
            "Russia",

            // 12
            "Russia",
            // 13
            "Russia",
            // 14
            "Russia",
            // 15
            "Russia",
            // 16
            "Russia",
            // 17
            "Russia",
            // 18
            "Russia"
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
            // 0
            "http://proxy.sidedark-warez.pl/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            // 1
            "http://efintrust.com/muro/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            // 2
            "http://prx.afkcz.eu/prx/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            // 4
            "http://ruhit.imgradio.pro/RusHit48?icy=http",
            // 5
            "http://stream.dancewave.online:8080/dance.mp3?icy=http",
            // 6
            "http://162.213.197.54/;?icy=http",
            // 7
            "http://185.39.195.90:8000/rusradio_128?icy=http",
            // 8
            "http://46.20.13.51:1230/;?icy=http",
            // 9
            "http://relay2.imgradio.pro/RetroHit?icy=http",
            // 10
            "http://85.25.209.152:8100/;?icy=http",
            // 11
            "http://dodofm.ru:8000/radio",

            // 12
            "http://193.232.148.42:8000/v12_1",
            // 13
            "http://retroserver.streamr.ru:8043/retro128",
            // 14
            "http://nashe3.hostingradio.ru/jazz-128.mp3",
            // 15
            "http://nashe3.hostingradio.ru/rock-128.mp3",
            // 16
            "http://icecast.orpheus.cdnvideo.ru:8000/orpheus_128",
            // 17
            "http://icecast.radiomontecarlo.cdnvideo.ru/mc.mp3",
            //18
            "http://bookradio.hostingradio.ru:8069/fm"
    };

    // Указать индекс (i - 1)
    public List<Integer> mLikes = new ArrayList<>();

    public static int mPlayRadioWithId = -1;
    public String mMetaDataNameSongPlayingRadio = null;

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
                mLikes.add(mIds.get(i));
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