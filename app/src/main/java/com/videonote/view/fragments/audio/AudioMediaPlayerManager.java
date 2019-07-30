package com.videonote.view.fragments.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.videonote.database.dto.RecordDTO;
import com.videonote.utils.TimeUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AudioMediaPlayerManager {
    private static AudioMediaPlayerManager instance = null;
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean dirty;
    private boolean paused;
    private AudioMediaPlayerManager(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();
        dirty = false;
    }

    public static AudioMediaPlayerManager getInstance(Context context){
        if(instance == null){
            instance = new AudioMediaPlayerManager(context);
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
        dirty = true;
    }

    public void pauseAudioPlayer(){
        mediaPlayer.pause();
        paused = true;
    }

    public void resumeAudioPlayer(){
        mediaPlayer.start();
        paused = false;
    }
    public void stopAudioPlayer(){
        mediaPlayer.stop();
        mediaPlayer.reset();   // You can reuse the object by going back to setAudioSource() step
        dirty = false;
        paused = false;
    }
    public void resetAudioPlayer(){
        mediaPlayer.reset();   // You can reuse the object by going back to setAudioSource() step
        dirty = false;
        paused = false;
    }
    public boolean isDirty(){
        return dirty;
    }
    public boolean isPaused(){
        return paused;
    }
    public void destroy(){
        mediaPlayer.release(); // Now the object cannot be reused
    }

    public long getTime(){
        return mediaPlayer.getCurrentPosition();
    }

    public String getFormattedTime(){
        long millis = mediaPlayer.getCurrentPosition();
        return TimeUtils.getFormattedTime(millis);
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

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

}
