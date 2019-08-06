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

    private enum MediaState {
        INIT,
        PLAYING,
        PAUSED,
        STOPPED,
        COMPLETED
    }
    private MediaState currentState;


    private AudioMediaPlayerManager(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();
        currentState = MediaState.INIT;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                if(currentState == MediaState.INIT){
                    return;
                }
                currentState = MediaState.COMPLETED;
            }
        });
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
        currentState = MediaState.PLAYING;
    }

    public void pauseAudioPlayer(){
        mediaPlayer.pause();
        currentState = MediaState.PAUSED;
    }

    public void resumeAudioPlayer(){
        mediaPlayer.start();
        currentState = MediaState.PLAYING;
    }
    public void stopAudioPlayer(){
        mediaPlayer.stop();
        mediaPlayer.reset();   // You can reuse the object by going back to setAudioSource() step
        currentState = MediaState.STOPPED;
    }
    public void resetAudioPlayer(){
        mediaPlayer.reset();   // You can reuse the object by going back to setAudioSource() step
        currentState = MediaState.STOPPED;
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

    public boolean isCompleted(){
        return currentState == MediaState.COMPLETED;
    }
    public boolean isPlaying(){
        return currentState == MediaState.PLAYING;
    }
    public boolean isPaused(){
        return currentState == MediaState.PAUSED;
    }
}
