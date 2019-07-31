package com.videonote.view.fragments.audio;

import android.content.Context;
import android.media.MediaRecorder;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.common.MediaRecorderManager;

public class AudioMediaRecorderManager extends MediaRecorderManager{
    private static AudioMediaRecorderManager instance = null;

    private AudioMediaRecorderManager(Context context){
        super(context);
    }

    public static AudioMediaRecorderManager getInstance(Context context){
        if(instance == null){
            instance = new AudioMediaRecorderManager(context);
        }else{
            instance.setContext(context);
        }
        return instance;
    }

    @Override
    public void startRecording(RecordDTO record) throws Exception{
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(record.getFileName());
        mediaRecorder.prepare();
        mediaRecorder.start();   // Recording is now started
        recording = true;
        initTime();
    }

    @Override
    public void stopRecording(){
        mediaRecorder.stop();
        recording = false;
        mediaRecorder.reset();   // You can reuse the object by going back to setAudioSource() step
    }
}
