package com.videonote.utils;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.SystemClock;

import com.videonote.database.dto.RecordDTO;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MediaRecorderHelper {
    private static MediaRecorderHelper instance = null;
    private MediaRecorder mediaRecorder;
    private Context context;
    private Long startTime = 0L;
    private Long totalRecordingTime = 0L;
    private boolean recording;

    private MediaRecorderHelper(Context context){
        this.context = context;
        mediaRecorder = new MediaRecorder();
        recording = false;
    }

    public static MediaRecorderHelper getInstance(Context context){
        if(instance == null){
            instance = new MediaRecorderHelper(context);
        }else{
            instance.setContext(context);
        }
        return instance;
    }
    private void setContext(Context context){
        this.context = context;
    }
    public void startAudioRecording(RecordDTO record) throws Exception{
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(record.getFileName());
        mediaRecorder.prepare();
        mediaRecorder.start();   // Recording is now started
        recording = true;
        initTime();
    }

    public void pauseAudioRecording(){
        startTime = 0L;
        recording = false;
    }

    public void resumeAudioRecording(){
        startTime = SystemClock.uptimeMillis();
        recording = true;
    }
    public void stopAudioRecording(){
        mediaRecorder.stop();
        recording = false;
        mediaRecorder.reset();   // You can reuse the object by going back to setAudioSource() step
    }

    public void destroy(){
        mediaRecorder.release(); // Now the object cannot be reused
    }

    public long getTime(){
        updateTime();
        return totalRecordingTime;
    }

    public String getFormattedTime(){
        long millis = getTime();
        String time = String.format(Locale.ITALY, "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return String.valueOf(time);
    }

    public void updateTime(){
        if(recording){
            totalRecordingTime += SystemClock.uptimeMillis() - startTime;
            startTime = SystemClock.uptimeMillis();
        }
    }

    public void initTime(){
        totalRecordingTime = 0L;
        startTime = SystemClock.uptimeMillis();
    }

    public void clean(){
        if(totalRecordingTime != 0L){
            if(recording) {
                stopAudioRecording();
            }else{
                mediaRecorder.reset();
            }
        }
        startTime = 0L;
        totalRecordingTime = 0L;
        recording = false;
    }

}
