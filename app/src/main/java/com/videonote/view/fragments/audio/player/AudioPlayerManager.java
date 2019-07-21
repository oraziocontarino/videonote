package com.videonote.view.fragments.audio.player;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaPlayerManager;
import com.videonote.utils.MediaRecorderManager;
import com.videonote.view.fragments.audio.AudioManager;
import com.videonote.view.fragments.audio.recorder.AudioRecorderList;

public class AudioPlayerManager extends AudioManager {
    private MediaPlayerManager mediaPlayerManager;
    //private RecordRepository recordRepository;
    private AudioPlayerList recordList;

    public AudioPlayerManager(Fragment fragment){
        super(fragment, R.id.audioPlayerStatusValue, R.id.audioPlayerStartButton, R.id.audioPlayerStopButton, R.id.audioPlayerPauseButton, R.id.audioPlayerResumeButton);
        // Init Database
        //recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaPlayerManager = MediaPlayerManager.getInstance(getView().getContext());

        // Init recording list
        recordList = new AudioPlayerList(fragment);
        //noteList.updateButtons(false);

        updateButton(true,false,false,false);
        //List<RecordDTO> records = recordRepository.getAllRecordsByType(Common.RECORD_TYPE.AUDIO);

        for(int i = 0; i<20; i++)
            recordList.generateRow("Test record "+i);
    }

    @Override
    protected void startButtonHandler(){
        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    statusLabel = "PLAYING";
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
                mediaPlayerManager.stopAudioRecording();
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
                mediaPlayerManager.pauseAudioRecording();
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
                mediaPlayerManager.resumeAudioRecording();
                statusLabel = "RECORDING";
                updateButton(false,true,true,false);
            }
        });
    }

    @Override
    protected void clean(){
        super.clean();
        mediaPlayerManager.clean();
        recordList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaPlayerManager == null ? "": mediaPlayerManager.getFormattedTime()+" - "+statusLabel;
    }
}
