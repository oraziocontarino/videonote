package com.videonote.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static String getFormattedTime(long millis){
        String time = String.format(Locale.ITALY, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return String.valueOf(time);
    }
}
