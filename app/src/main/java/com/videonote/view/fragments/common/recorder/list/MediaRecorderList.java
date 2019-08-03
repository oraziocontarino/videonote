package com.videonote.view.fragments.common.recorder.list;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videonote.view.Common;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.dto.RecordDTO;
import com.videonote.utils.FileUtils;
import com.videonote.view.fragments.common.MediaRecorderManager;

public abstract class MediaRecorderList {
    protected Fragment fragment;
    protected LinearLayout noteList;
    protected ImageButton addTextNoteButton;
    protected NoteDTO noteDTO;
    protected RecordDTO recordDTO;
    protected EditText noteText;
    protected NoteRepository noteRepository;
    protected String noteSuffix;
    protected MediaRecorderManager mediaRecorderManager;

    public MediaRecorderList(Fragment fragment, MediaRecorderManager mediaRecorderManager, int recordNoteContainerId, int recordTextButtonId, int recordTextInputId){
        noteList = fragment.getView().findViewById(recordNoteContainerId);
        addTextNoteButton = fragment.getView().findViewById(recordTextButtonId);
        noteText = fragment.getView().findViewById(recordTextInputId);
        noteDTO = new NoteDTO();
        recordDTO = new RecordDTO();
        noteRepository = DatabaseManager.getInstance(fragment.getContext()).getNoteRepository();
        this.fragment = fragment;
        this.mediaRecorderManager = mediaRecorderManager;

        addTextNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickEvt();
            }
        });
    }

    private void onClickEvt(){
        if(noteText == null || noteText.getText().toString().trim().length() == 0){
            return;
        }
        //AudioMediaRecorderManager.getInstance(getContext())
        updateTextNoteDTO(mediaRecorderManager);
        storeTextNoteToFile();
        storeNoteToDB();
        addNoteToList();
        clearNoteText(true);
    }

    protected void clearNoteText(boolean clearText){
        if(clearText) {
            noteText.setText("");
        }
        noteText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }



    protected void updateTextNoteDTO(MediaRecorderManager recorderManager){
        noteDTO.setFileName(FileUtils.getUniqueName("text_note.txt"));
        noteDTO.setRecordId(recordDTO.getId());
        noteDTO.setStartTime(recorderManager.getTime());
        noteDTO.setType(Common.NOTE_TYPE.TEXT.name());
    }

    protected void storeTextNoteToFile(){
        String note = noteText.getText().toString();
        FileUtils.saveTextFile(fragment, noteDTO.getFileName(), note);
    }

    protected void storeNoteToDB(){
        noteRepository.insert(noteDTO);
    }

    protected void addNoteToList(){
        LinearLayout wrapper = new LinearLayout(fragment.getContext());
        wrapper.addView(generateLabel());
        wrapper.addView(generateButton(wrapper));
        noteList.addView(wrapper);
    }



    protected TextView generateLabel(){
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

    protected Button generateButton(LinearLayout wrapper){
        Button button = new Button(fragment.getContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        button.setOnClickListener(new MediaRecorderListButtonListener(fragment, wrapper, noteList, noteDTO.getFileName()));
        button.setText("Remove");
        return button;
    }

    public void updateRecordDTO(String fileName, Common.RECORD_TYPE type){
        this.recordDTO.setFileName(fileName);
        this.recordDTO.setType(type.name());
    }
    public void updateRecordDTO(long id){
        this.recordDTO.setId(id);
    }

    public RecordDTO getRecordDTO(){
        return this.recordDTO;
    }
    public void updateButtons(boolean enabled){
        addTextNoteButton.setEnabled(enabled);
    }

    public void clean(){
        noteList.removeAllViewsInLayout();
        clearNoteText(true);
        updateButtons(false);
    }

    protected Context getContext(){
        return fragment.getContext();
    }
}
