package com.videonote.view.fragments.audio.player;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.MediaPlayerManager;
import com.videonote.view.fragments.audio.AudioManager;
import com.videonote.view.fragments.audio.player.list.AudioPlayerListRow;

import java.util.List;

public class AudioPlayerManager extends AudioManager {
    private MediaPlayerManager mediaPlayerManager;
    private RecordRepository recordRepository;
    private LinearLayout recordsList;

    public AudioPlayerManager(Fragment fragment){
        super(fragment, R.id.audioPlayerStatusValue, R.id.audioPlayerStartButton, R.id.audioPlayerStopButton, R.id.audioPlayerPauseButton, R.id.audioPlayerResumeButton);
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaPlayerManager = MediaPlayerManager.getInstance(getView().getContext());

        // Init recording list
        initRecordsList();

        updateButton(false,false,false,false);
    }




    @Override
    public void startAction(RecordDTO record) {
        try{
            mediaPlayerManager.startAudioPlayer(record);
            startAction();
        }catch (Exception e){

        }
    }
    @Override
    public void startAction() {
        statusLabel = "PLAYING";
        updateButton(false, true, true, false);
    }

    @Override
    public void stopAction() {
        try{
            mediaPlayerManager.stopAudioPlayer();
            statusLabel = "STOPPED";
            updateButton(false,false,false,false);
        }catch (Exception e){
        //...
        }
    }

    @Override
    public void pauseAction() {
        try{
            mediaPlayerManager.pauseAudioPlayer();
            statusLabel = "STOPPED";
            updateButton(false,true,false,true);
        }catch (Exception e){
            //...
        }
    }

    @Override
    public void resumeAction() {
        try{
            mediaPlayerManager.resumeAudioPlayer();
            statusLabel = "PLAYING";
            updateButton(false,true,true,false);
        }catch (Exception e){
            //...
        }
    }

    @Override
    protected void clean(){
        super.clean();
        mediaPlayerManager.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaPlayerManager == null ? "": mediaPlayerManager.getFormattedTime()+" - "+statusLabel;
    }

    private void initRecordsList(){
        recordsList = getView().findViewById(R.id.audioPlayerRecordsContainer);
        List<RecordDTO> records = recordRepository.getByType(Common.RECORD_TYPE.AUDIO);
        for(final RecordDTO record : records){
            recordsList.addView(new AudioPlayerListRow(this, record));
        }
    }
}
