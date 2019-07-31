package com.videonote.view.fragments.common;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.SystemClock;

import com.videonote.database.dto.RecordDTO;

public abstract class MediaRecorderManager {
    protected MediaRecorder mediaRecorder;
    protected Context context;
    protected Long startTime = 0L;
    protected Long totalRecordingTime = 0L;
    protected boolean recording;

    public MediaRecorderManager(Context context){
        this.context = context;
        mediaRecorder = new MediaRecorder();
        recording = false;
    }

    protected void setContext(Context context){
        this.context = context;
    }
    public long getTime(){
        updateTime();
        return totalRecordingTime;
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
                stopRecording();
            }else{
                mediaRecorder.reset();
            }
        }
        startTime = 0L;
        totalRecordingTime = 0L;
        recording = false;
    }
    public abstract void startRecording(RecordDTO record) throws Exception;

    public abstract void stopRecording();

}
