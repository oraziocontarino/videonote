package com.videonote.view.fragments.video.recorder;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.view.fragments.video.VideoMediaRecorderManager;
import com.videonote.view.fragments.utils.MediaRecorderUIController;

public class VideoRecorderController extends MediaRecorderUIController {
    private VideoMediaRecorderManager videoMediaRecorderManager;
    private RecordRepository recordRepository;
    //private AudioRecorderListManager noteList;

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
//        noteList = new AudioRecorderListManager(fragment);
//        noteList.updateButtons(false);
        updateButton(true,false);
    }

    @Override
    protected void startAction(){
        try{
            //noteList.clean();
            //noteList.updateRecordDTO(FileUtils.getFilePath(getContext(), "video.mp4", true));
            //            videoMediaRecorderManager.startVideoRecording(noteList.getRecordDTO());
            videoMediaRecorderManager.startVideoRecording(null);
            //recordRepository.insert(noteList.getRecordDTO());
            //noteList.updateButtons(true);
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
        videoMediaRecorderManager.stopAudioRecording();
        statusLabel = "STOPPED";
        updateButton(true,false);
    }

    @Override
    protected void clean(){
        super.clean();
        videoMediaRecorderManager.clean();
        //noteList.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = videoMediaRecorderManager == null ? "": videoMediaRecorderManager.getFormattedTime()+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){

    }
}