package com.videonote.view.fragments.audio.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.videonote.view.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.TimeUtils;
import com.videonote.view.fragments.audio.AudioMediaRecorderManager;
import com.videonote.utils.FileUtils;
import com.videonote.view.fragments.common.MediaRecorderUIController;
import com.videonote.view.fragments.dashboard.CustomLocationManager;

public class AudioRecorderController extends MediaRecorderUIController {
    private AudioMediaRecorderManager audioMediaRecorderManager;
    private RecordRepository recordRepository;
    private AudioRecorderListManager noteList;

    public AudioRecorderController(Fragment fragment){
        super(fragment, R.id.audioRecordStatusLabel, R.id.audioRecordStatusValue, R.id.audioRecordStartButton, R.id.audioRecordStopButton);
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        audioMediaRecorderManager = AudioMediaRecorderManager.getInstance(getView().getContext());

        // Init recording list
        noteList = new AudioRecorderListManager(fragment);
        noteList.updateButtons(false);
        updateButton(true,false);
    }

    @Override
    protected void startAction(){
        try{
            noteList.clean();
            noteList.updateRecordDTO(FileUtils.getFilePath(getContext(), "audio.3gp", true), Common.RECORD_TYPE.AUDIO);
            audioMediaRecorderManager.startRecording(noteList.getRecordDTO());
            recordRepository.insert(noteList.getRecordDTO());
            noteList.updateButtons(true);
            statusLabel = "RECORDING";
            updateButton(false,true);
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
        AudioMediaRecorderManager.getInstance(getContext()).stopRecording();
        statusLabel = "STOPPED";
        updateButton(true,false);
    }

    @Override
    protected void clean(){
        super.clean();
        audioMediaRecorderManager.clean();
        noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = audioMediaRecorderManager == null ? "": TimeUtils.getFormattedTime(audioMediaRecorderManager.getTime())+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){

    }
}
