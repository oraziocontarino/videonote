package com.videonote.view.fragments.audio.player;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaRecorderHelper;

public class RecordingList {
    private Fragment fragment;
    private LinearLayout recordsList;
    private Button addTextNoteButton;
    private Button addFileNoteButton;
    private NoteDTO noteDTO;
    private RecordDTO recordDTO;
    private EditText noteText;
    private EditText noteFile;
    private NoteRepository noteRepository;

    public RecordingList(final Fragment fragment){
        this.fragment = fragment;
        recordsList = (LinearLayout) fragment.getView().findViewById(R.id.audioPlayerRecordsContainer);
    }

    public void clean(){

    }
}

