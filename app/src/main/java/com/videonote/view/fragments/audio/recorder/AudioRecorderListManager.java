package com.videonote.view.fragments.audio.recorder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videonote.Common;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.dto.RecordDTO;
import com.videonote.R;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaPhotoManager;
import com.videonote.utils.MediaRecorderManager;

public class AudioRecorderListManager {
    private Fragment fragment;
    private LinearLayout noteList;
    private ImageButton addTextNoteButton;
    private Button cameraOpenButton;
    private ImageButton cameraCloseButton;
    private Button cameraCaptureButton;
    private NoteDTO noteDTO;
    private RecordDTO recordDTO;
    private EditText noteText;
    private NoteRepository noteRepository;
    private MediaPhotoManager mediaPhotoManager;

    public AudioRecorderListManager(final Fragment fragment){
        noteList = fragment.getView().findViewById(R.id.audioRecordNoteContainer);
        addTextNoteButton = fragment.getView().findViewById(R.id.audioRecordTextButton);
        cameraOpenButton = fragment.getView().findViewById(R.id.cameraOpenButton);
        cameraCloseButton = fragment.getView().findViewById(R.id.cameraCloseButton);
        cameraCaptureButton = fragment.getView().findViewById(R.id.cameraCaptureButton);
        noteText = (EditText) fragment.getView().findViewById(R.id.audioRecordTextInput);
        noteDTO = new NoteDTO();
        recordDTO = new RecordDTO();
        noteRepository = DatabaseManager.getInstance(fragment.getContext()).getNoteRepository();
        this.fragment = fragment;
        mediaPhotoManager = new MediaPhotoManager(fragment);

        addTextNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateTextNoteDTO();
                storeTextNoteToFile();
                storeNoteToDB();
                addNoteToList();
                noteText.setText("");
            }
        });

        cameraOpenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mediaPhotoManager.openCamera();
                cameraOpenButton.setVisibility(View.GONE);
                cameraCloseButton.setVisibility(View.VISIBLE);
                cameraCaptureButton.setVisibility(View.VISIBLE);

            }
        });
        cameraCloseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mediaPhotoManager.closeCamera();
                cameraOpenButton.setVisibility(View.VISIBLE);
                cameraCloseButton.setVisibility(View.GONE);
                cameraCaptureButton.setVisibility(View.GONE);
            }
        });
        cameraCaptureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updatePictureNoteDTO();;
                storePictureNoteToFile();
                storeNoteToDB();
                addNoteToList();
            }
        });

    }

    private void updateTextNoteDTO(){
        noteDTO.setFileName(FileUtils.getUniqueName("text_note.txt"));
        noteDTO.setRecordId(recordDTO.getId());
        noteDTO.setStartTime(MediaRecorderManager.getInstance(getContext()).getTime());
        noteDTO.setType(Common.NOTE_TYPE.TEXT.name());
    }

    private void storeTextNoteToFile(){
        String note = noteText.getText().toString();
        FileUtils.saveTextFile(fragment, noteDTO.getFileName(), note);
    }

    private void storeNoteToDB(){
        noteRepository.insert(noteDTO);
    }

    private void addNoteToList(){
        LinearLayout wrapper = new LinearLayout(fragment.getContext());
        wrapper.addView(generateLabel());
        wrapper.addView(generateButton(wrapper));
        noteList.addView(wrapper);
    }

    private void updatePictureNoteDTO(){
        noteDTO.setFileName(FileUtils.getUniqueName("picture_note.jpg"));
        noteDTO.setRecordId(recordDTO.getId());
        noteDTO.setStartTime(MediaRecorderManager.getInstance(getContext()).getTime());
        noteDTO.setType(Common.NOTE_TYPE.PICTURE.name());
    }

    private void storePictureNoteToFile(){
        mediaPhotoManager.takePicture(noteDTO);
    }

    private TextView generateLabel(){
        TextView label = new TextView(fragment.getContext());
        label.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        String content;
        if(noteDTO.getType().equals(Common.NOTE_TYPE.PICTURE.name()))
        {
            content = FileUtils.getFileNameFromPath(noteDTO.getFileName());
        }else {
            content = FileUtils.readTextFile(fragment, noteDTO.getFileName());
        }
        label.setText(content);
        return label;
    }

    private Button generateButton(final LinearLayout wrapper){
        Button button = new Button(fragment.getContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        button.setOnClickListener(new View.OnClickListener() {
            private NoteDTO toDelete = noteDTO;
            public void onClick(View v) {
                Log.d("AUDIO", "BUTTON PRESSED!");
                FileUtils.deleteFile(getContext(), toDelete.getFileName());
                noteList.removeView(wrapper);
            }
        });
        button.setText("Remove");
        return button;
    }

    public void updateRecordDTO(String fileName){
        this.recordDTO.setFileName(fileName);
        this.recordDTO.setType(Common.RECORD_TYPE.AUDIO.name());
    }
    public void updateRecordDTO(long id){
        this.recordDTO.setId(id);
    }

    public RecordDTO getRecordDTO(){
        return this.recordDTO;
    }
    public void updateButtons(boolean enabled){
        addTextNoteButton.setEnabled(enabled);
        cameraOpenButton.setEnabled(enabled);

    }

    public void clean(){
        noteList.removeAllViewsInLayout();
        noteText.setText("");
        updateButtons(false);
    }

    private Context getContext(){
        return fragment.getContext();
    }
}
