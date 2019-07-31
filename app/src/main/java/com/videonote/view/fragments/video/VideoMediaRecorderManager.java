package com.videonote.view.fragments.video;

import android.support.v4.app.Fragment;

import com.videonote.database.dto.RecordDTO;
import com.videonote.utils.MediaVideoManager;
import com.videonote.view.fragments.common.MediaRecorderManager;

public class VideoMediaRecorderManager extends MediaRecorderManager{
    private static VideoMediaRecorderManager instance = null;
    private MediaVideoManager mediaVideoManager;

    private VideoMediaRecorderManager(Fragment fragment){
        super(fragment.getContext());
        mediaVideoManager = new MediaVideoManager(fragment);
    }

    public static VideoMediaRecorderManager getInstance(Fragment fragment){
        if(instance == null){
            instance = new VideoMediaRecorderManager(fragment);
        }else{
            instance.setContext(fragment.getContext());
        }
        return instance;
    }
    public void openCamera() {
        mediaVideoManager.onResume();
    }

    @Override
    public void startRecording(RecordDTO record) throws Exception{
        mediaVideoManager.startRecordingVideo(record);
        recording = true;
        initTime();
    }

    @Override
    public void stopRecording(){
        mediaVideoManager.stopRecordingVideo();
        recording = false;
    }

    @Override
    public void clean(){
        mediaVideoManager.closeCamera();
        super.clean();
    }
}
