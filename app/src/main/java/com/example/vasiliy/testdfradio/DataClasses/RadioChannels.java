package com.example.vasiliy.testdfradio.DataClasses;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by vasiliy on 27.11.16.
 */

public class RadioChannels {
    public int ids[] = {
            1,
            2,
            3,
            4
    };

    public String names[] = {
            "CRAZY DREAM",
            "CRAZY DREAM",
            "CRAZY DREAM",
            "CRAZY DREAM"
    };

    public String locations[] = {
            "Moscow, Russia",
            "Moscow, Russia",
            "Moscow, Russia",
            "Moscow, Russia"
    };

    public String links[] = {
            "http://proxy.sidedark-warez.pl/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://leather-bg.com/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://prx.afkcz.eu/prx/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D",
            "http://efintrust.com/muro/index.php?q=aHR0cDovL3N0cmVhbWluZy5yYWRpb25vbXkuY29tL1JhZGlvVGVzdC12MTA%3D"
    };

    public List<Integer> likes = new ArrayList<>();
}
