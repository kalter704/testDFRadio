package com.example.vasiliy.testdfradio.DataClasses;

import java.util.ArrayList;
import java.util.List;

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

    public int mIds[] = {
            1,
            2,
            3,
            4
    };

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

    public String mLinks[] = {
            "http://proxy.sidedark-warez.pl/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://leather-bg.com/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://prx.afkcz.eu/prx/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://efintrust.com/muro/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D"
    };

    // Указать индекс (i - 1)
    public List<Integer> likes = new ArrayList<>();
}
