package com.videonote.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.util.Log;

import com.videonote.database.dto.RecordDTO;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MediaPlayerManager {
    private static MediaPlayerManager instance = null;
    private MediaPlayer mediaPlayer;
    private Context context;

    private MediaPlayerManager(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }

    public static MediaPlayerManager getInstance(Context context){
        if(instance == null){
            instance = new MediaPlayerManager(context);
        }else{
            instance.setContext(context);
        }
        return instance;
    }
    private void setContext(Context context){
        this.context = context;
    }
    public void startAudioPlayer(RecordDTO record) throws Exception{
        mediaPlayer.setDataSource(record.getFileName());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void pauseAudioPlayer(){
        mediaPlayer.pause();
    }

    public void resumeAudioPlayer(){
        mediaPlayer.start();
    }
    public void stopAudioPlayer(){
        mediaPlayer.stop();
        mediaPlayer.reset();   // You can reuse the object by going back to setAudioSource() step
    }

    public void destroy(){
        mediaPlayer.release(); // Now the object cannot be reused
    }

    public long getTime(){
        return mediaPlayer.getCurrentPosition();
    }

    public String getFormattedTime(){
        long millis = mediaPlayer.getCurrentPosition();
        return getFormattedTime(millis);
    }
    public static String getFormattedTime(long millis){
        String time = String.format(Locale.ITALY, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return String.valueOf(time);
    }

    public void clean(){
        if(mediaPlayer.getCurrentPosition() != 0L){
            if(mediaPlayer.isPlaying()) {
                stopAudioPlayer();
            }else{
                mediaPlayer.reset();
            }
        }
    }

}
