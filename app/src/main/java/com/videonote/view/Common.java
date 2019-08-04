package com.videonote.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Common {
    public static final String EOL = "\r\n";
    public enum RECORD_TYPE {
        AUDIO,
        VIDEO
    }
    public enum NOTE_TYPE {
        TEXT,
        PICTURE
    }

}
