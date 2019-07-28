package com.videonote.view.fragments.video.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaRecorderManager;
import com.videonote.utils.MediaVideoManager;
import com.videonote.view.fragments.audio.AudioManager;
import com.videonote.view.fragments.audio.recorder.AudioRecorderListManager;

public class VideoRecorderManager extends AudioManager {
    private MediaRecorderManager mediaRecorderManager;
    private RecordRepository recordRepository;
    private MediaVideoManager mediaVideoManager;
    //private AudioRecorderListManager noteList;

    public VideoRecorderManager(Fragment fragment){
        super(
                fragment,
                R.id.videoRecordStatusLabel,
                R.id.videoRecordStatusValue,
                R.id.videoRecordStartButton,
                R.id.videoRecordStopButton,
                R.id.videoRecordPauseButton,
                R.id.videoRecordResumeButton
        );
        mediaVideoManager = new MediaVideoManager(fragment);
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaRecorderManager = MediaRecorderManager.getInstance(getView().getContext());

        // Init recording list
//        noteList = new AudioRecorderListManager(fragment);
//        noteList.updateButtons(false);

        mediaVideoManager.openCamera();
        updateButton(true,false,false,false);
    }

    @Override
    protected void startAction(){
        try{
            //noteList.clean();
            //noteList.updateRecordDTO(FileUtils.getFilePath(getContext(), "video.mp4", true));
            //mediaRecorderManager.startAudioRecording(noteList.getRecordDTO());
            //recordRepository.insert(noteList.getRecordDTO());
            //noteList.updateButtons(true);
            mediaVideoManager.startRecordingVideo();
            statusLabel = "RECORDING";
            updateButton(false,true,true,false);
        }catch(Exception e){
            //TODO: print error message in toast notification
            Log.d("AUDIO", e.getMessage() == null ? "null": e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    protected void startAction(RecordDTO dto){}

    @Override
    protected void stopAction(){
        mediaVideoManager.stopRecordingVideo();
        //MediaRecorderManager.getInstance(getContext()).stopAudioRecording();
        statusLabel = "STOPPED";
        updateButton(true,false,false,false);
    }

    @Override
    protected void pauseAction(){
        MediaRecorderManager.getInstance(getContext()).pauseAudioRecording();
        statusLabel = "PAUSED";
        updateButton(false,true,false,true);
    }
    @Override
    protected void resumeAction(){
        mediaRecorderManager.resumeAudioRecording();
        statusLabel = "RECORDING";
        updateButton(false,true,true,false);
    }

    @Override
    protected void clean(){
        super.clean();
        mediaRecorderManager.clean();
        //noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaRecorderManager == null ? "": mediaRecorderManager.getFormattedTime()+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){

    }
}
