package com.videonote.fragments.audiorecording;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videonote.Database.Common;
import com.videonote.Database.NoteDTO;
import com.videonote.Database.RecordDTO;
import com.videonote.R;
import com.videonote.Utils.FileUtils;
import com.videonote.Utils.MediaRecorderHelper;

public class AudioNoteList {
    private Fragment fragment;
    private LinearLayout noteList;
    private Button addTextNoteButton;
    private Button addFileNoteButton;
    private NoteDTO noteDTO;
    private RecordDTO recordDTO;
    private EditText noteText;
    private EditText noteFile;

    public AudioNoteList(Fragment fragment){
        noteList = (LinearLayout) fragment.getView().findViewById(R.id.audioRecordNoteContainer);
        addTextNoteButton = (Button) fragment.getView().findViewById(R.id.audioRecordTextButton);
        addFileNoteButton = (Button) fragment.getView().findViewById(R.id.audioRecordFileButton);
        noteText = (EditText) fragment.getView().findViewById(R.id.audioRecordTextInput);
        noteFile = (EditText) fragment.getView().findViewById(R.id.audioRecordFileInput);
        noteDTO = new NoteDTO();
        recordDTO = new RecordDTO();
        this.fragment = fragment;

        addTextNoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                noteDTO.setFileName(FileUtils.getUniqueName("text_note.txt"));
                noteDTO.setRecordId(recordDTO.getId());
                noteDTO.setStartTime(MediaRecorderHelper.getInstance(view.getContext()).getTime());
                noteDTO.setType(Common.NOTE_TYPE.TEXT.name());
                storeTextNote();
                add();
            }
        });
    }

    private void storeTextNote(){
        String note = noteText.getText().toString();
        FileUtils.saveTextFile(fragment, noteDTO.getFileName(), note);
    }

    private void add(){
        LinearLayout wrapper = new LinearLayout(fragment.getContext());
        wrapper.addView(generateLabel());
        wrapper.addView(generateButton(wrapper));
        noteList.addView(wrapper);
    }

    private TextView generateLabel(){
        TextView label = new TextView(fragment.getContext());
        label.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        String content = FileUtils.readTextFile(fragment, noteDTO.getFileName());
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
                TextView textView = null;
                FileUtils.deleteFile(fragment, toDelete.getFileName());
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

    public RecordDTO getRecordDTO(){
        return this.recordDTO;
    }
    public void updateButtons(boolean enabled){
        noteFile.setEnabled(enabled);
        addTextNoteButton.setEnabled(enabled);
        noteFile.setEnabled(enabled);
        addFileNoteButton.setEnabled(enabled);
    }
}
