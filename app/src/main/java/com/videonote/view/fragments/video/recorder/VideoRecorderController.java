package com.videonote.view.fragments.video.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.TimeUtils;
import com.videonote.view.fragments.video.VideoMediaRecorderManager;
import com.videonote.view.fragments.common.MediaRecorderUIController;

public class VideoRecorderController extends MediaRecorderUIController {
    private VideoMediaRecorderManager videoMediaRecorderManager;
    private RecordRepository recordRepository;
    private VideoRecorderListManager noteList;

    public VideoRecorderController(Fragment fragment){
        super(
                fragment,
                R.id.videoRecordStatusLabel,
                R.id.videoRecordStatusValue,
                R.id.videoRecordStartButton,
                R.id.videoRecordStopButton
        );
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        videoMediaRecorderManager = VideoMediaRecorderManager.getInstance(fragment);
        videoMediaRecorderManager.openCamera();

        // Init recording list
        noteList = new VideoRecorderListManager(fragment);
        noteList.updateButtons(false);
        updateButton(true,false);
    }

    @Override
    protected void startAction(){
        try{
            noteList.clean();
            noteList.updateRecordDTO(FileUtils.getFilePath(getContext(), "video.mp4", true), Common.RECORD_TYPE.VIDEO);
            videoMediaRecorderManager.startRecording(noteList.getRecordDTO());
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
        videoMediaRecorderManager.stopRecording();
        statusLabel = "STOPPED";
        updateButton(true,false);
    }

    @Override
    protected void clean(){
        super.clean();
        videoMediaRecorderManager.clean();
        noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = videoMediaRecorderManager == null ? "": TimeUtils.getFormattedTime(videoMediaRecorderManager.getTime())+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){

    }
}
