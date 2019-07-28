package com.videonote.view.fragments.video;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.Fragment;

import com.videonote.database.dto.RecordDTO;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VideoMediaRecorderManager {
    private static VideoMediaRecorderManager instance = null;
    private VideoMediaRecorderController videoMediaRecorderController;
    private Context context;
    private Long startTime = 0L;
    private Long totalRecordingTime = 0L;
    private boolean recording;

    private VideoMediaRecorderManager(Fragment fragment){
        this.context = fragment.getContext();
        videoMediaRecorderController = new VideoMediaRecorderController(fragment);
        recording = false;
    }

    public static VideoMediaRecorderManager getInstance(Fragment fragment){
        if(instance == null){
            instance = new VideoMediaRecorderManager(fragment);
        }else{
            instance.setContext(fragment.getContext());
        }
        return instance;
    }
    private void setContext(Context context){
        this.context = context;
    }
    public void openCamera() {
        videoMediaRecorderController.openCamera();
    }

    public void startVideoRecording(RecordDTO record) throws Exception{
        videoMediaRecorderController.startRecordingVideo();
        recording = true;
        initTime();
    }

    public void pauseAudioRecording(){
        videoMediaRecorderController.onPause();
        startTime = 0L;
        recording = false;
    }

    public void resumeAudioRecording(){
        videoMediaRecorderController.onResume();
        startTime = SystemClock.uptimeMillis();
        recording = true;
    }
    public void stopAudioRecording(){
        videoMediaRecorderController.stopRecordingVideo();
        recording = false;
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
            }
        }
        startTime = 0L;
        totalRecordingTime = 0L;
        recording = false;
    }






}
