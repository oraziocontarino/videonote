package com.videonote.view.fragments.audio.recorder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaPhotoManager;
import com.videonote.view.fragments.audio.AudioMediaRecorderManager;
import com.videonote.view.fragments.common.recorder.list.MediaRecorderList;

public class AudioRecorderListManager extends MediaRecorderList {
    private Button cameraOpenButton;
    private ImageButton cameraCloseButton;
    private Button cameraCaptureButton;
    private MediaPhotoManager mediaPhotoManager;

    public AudioRecorderListManager(Fragment fragment){
        super(fragment, R.id.audioRecordNoteContainer, R.id.audioRecordTextButton, R.id.audioRecordTextInput);
        cameraOpenButton = fragment.getView().findViewById(R.id.cameraOpenButton);
        cameraCloseButton = fragment.getView().findViewById(R.id.cameraCloseButton);
        cameraCaptureButton = fragment.getView().findViewById(R.id.cameraCaptureButton);
        mediaPhotoManager = new MediaPhotoManager(fragment);
        initCameraListeners();
    }

    private void initCameraListeners(){
        cameraOpenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mediaPhotoManager.openCamera();
                cameraOpenButton.setVisibility(View.GONE);
                cameraCloseButton.setVisibility(View.VISIBLE);
                cameraCaptureButton.setVisibility(View.VISIBLE);
                clearNoteText(false);

            }
        });
        cameraCloseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                closeCameraAction();
            }
        });
        cameraCaptureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!mediaPhotoManager.isReady()){
                    return;
                }
                updatePictureNoteDTO();
                storePictureNoteToFile();
                storeNoteToDB();
                addNoteToList();
                clearNoteText(false);
            }
        });
    }

    protected void closeCameraAction(){
        mediaPhotoManager.closeCamera();
        cameraOpenButton.setVisibility(View.VISIBLE);
        cameraCloseButton.setVisibility(View.GONE);
        cameraCaptureButton.setVisibility(View.GONE);
        noteText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    protected void updatePictureNoteDTO(){
        noteDTO.setFileName(FileUtils.getUniqueName("picture_note.jpg"));
        noteDTO.setRecordId(recordDTO.getId());
        noteDTO.setStartTime(AudioMediaRecorderManager.getInstance(getContext()).getTime());
        noteDTO.setType(Common.NOTE_TYPE.PICTURE.name());
    }

    protected void storePictureNoteToFile(){
        mediaPhotoManager.takePicture(noteDTO);
    }

    @Override
    public void updateButtons(boolean enabled){
        super.updateButtons(enabled);
        cameraOpenButton.setEnabled(enabled);
    }
    @Override
    public void clean(){
        super.clean();
        closeCameraAction();
    }



}
