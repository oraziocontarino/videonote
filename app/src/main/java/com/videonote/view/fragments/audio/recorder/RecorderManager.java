package com.videonote.view.fragments.audio.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaRecorderHelper;
import com.videonote.view.fragments.audio.AudioManager;

import org.w3c.dom.NodeList;

public class RecorderManager extends AudioManager {
    private MediaRecorderHelper mediaRecorderHelper;
    private RecordRepository recordRepository;
    private AudioNoteList noteList;

    public RecorderManager(Fragment fragment){
        super(fragment, R.id.audioRecordStatusValue, R.id.audioRecordStartButton, R.id.audioRecordStopButton, R.id.audioRecordPauseButton, R.id.audioRecordResumeButton);
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaRecorderHelper = MediaRecorderHelper.getInstance(getView().getContext());

        // Init recording list
        noteList = new AudioNoteList(fragment);
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
                    mediaRecorderHelper.startAudioRecording(noteList.getRecordDTO());
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
                MediaRecorderHelper.getInstance(view.getContext()).stopAudioRecording();
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
                MediaRecorderHelper.getInstance(view.getContext()).pauseAudioRecording();
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
                mediaRecorderHelper.resumeAudioRecording();
                statusLabel = "RECORDING";
                updateButton(false,true,true,false);
            }
        });
    }

    @Override
    protected void clean(){
        super.clean();
        mediaRecorderHelper.clean();
        noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaRecorderHelper == null ? "": mediaRecorderHelper.getFormattedTime()+" - "+statusLabel;
    }
}
