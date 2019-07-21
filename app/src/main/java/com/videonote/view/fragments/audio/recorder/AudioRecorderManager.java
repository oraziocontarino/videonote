package com.videonote.view.fragments.audio.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaRecorderManager;
import com.videonote.view.fragments.audio.AudioManager;

public class AudioRecorderManager extends AudioManager {
    private MediaRecorderManager mediaRecorderManager;
    private RecordRepository recordRepository;
    private AudioRecorderList noteList;

    public AudioRecorderManager(Fragment fragment){
        super(fragment, R.id.audioRecordStatusValue, R.id.audioRecordStartButton, R.id.audioRecordStopButton, R.id.audioRecordPauseButton, R.id.audioRecordResumeButton);
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaRecorderManager = MediaRecorderManager.getInstance(getView().getContext());

        // Init recording list
        noteList = new AudioRecorderList(fragment);
        noteList.updateButtons(false);

        updateButton(true,false,false,false);
        //List<RecordDTO> records = recordRepository.getAllRecordsByType(Common.RECORD_TYPE.AUDIO);
    }

    @Override
    protected void startButtonHandler(){
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    noteList.updateRecordDTO(FileUtils.getFilePath(view.getContext(), "audio.3gp", true));
                    mediaRecorderManager.startAudioRecording(noteList.getRecordDTO());
                    recordRepository.insert(noteList.getRecordDTO());
                    noteList.updateButtons(true);
                    statusLabel = "RECORDING";
                    updateButton(false,true,true,false);
                }catch(Exception e){
                    //TODO: print error message in toast notification
                    Log.d("AUDIO", e.getMessage() == null ? "null": e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void stopButtonHandler(){
        stopRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: set audio length with info in totalRecordingTime
                MediaRecorderManager.getInstance(view.getContext()).stopAudioRecording();
                statusLabel = "STOPPED";
                updateButton(true,false,false,false);
            }
        });
    }

    @Override
    protected void pauseButtonHandler(){
        pauseRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaRecorderManager.getInstance(view.getContext()).pauseAudioRecording();
                statusLabel = "PAUSED";
                updateButton(false,true,false,true);
            }
        });
    }
    @Override
    protected void resumeButtonHandler(){
        resumeRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorderManager.resumeAudioRecording();
                statusLabel = "RECORDING";
                updateButton(false,true,true,false);
            }
        });
    }

    @Override
    protected void clean(){
        super.clean();
        mediaRecorderManager.clean();
        noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaRecorderManager == null ? "": mediaRecorderManager.getFormattedTime()+" - "+statusLabel;
    }
}
