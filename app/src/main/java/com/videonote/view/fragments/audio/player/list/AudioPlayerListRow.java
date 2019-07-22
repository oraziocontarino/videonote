package com.videonote.view.fragments.audio.player.list;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.view.fragments.audio.player.AudioPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class AudioPlayerListRow extends LinearLayout {
    private LayoutParams layout;
    private AudioPlayerListRowHeader header;
    private List<LinearLayout> details;
    private NoteRepository noteRepository;

    public AudioPlayerListRow(AudioPlayerManager manager, RecordDTO recordDTO){
        super(manager.getContext());

        layout = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.VERTICAL);
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();

        prepareHeader(manager, recordDTO);
        prepareDetail(manager, recordDTO);
        setRow(manager, recordDTO);
    }
    private void prepareHeader(AudioPlayerManager manager, RecordDTO recordDTO){
        header = new AudioPlayerListRowHeader(this, manager, recordDTO);
    }
    private void setRow(AudioPlayerManager manager, RecordDTO recordDTO){
        super.addView(header);
        for(LinearLayout row : details){
            super.addView(row);
        }
    }

    private void prepareDetail(AudioPlayerManager manager, RecordDTO recordDTO){
        List<NoteDTO> notes = noteRepository.getByRecordId(recordDTO.getId());
        details = new ArrayList<LinearLayout>();
        for(NoteDTO note : notes){
            details.add(new AudioPlayerListRowDetailText(manager.getContext(), "Description", note.getFileName(), note));
            details.add(new AudioPlayerListRowDetailText(manager.getContext(), "Time", String.valueOf(note.getStartTime()), note));
            details.add(new AudioPlayerListRowDetailText(manager.getContext(), "Delete", "2x btn change fn", note));
        }
    }

    public void toggleDetail(){
        if(details.size() == 0){
            return;
        }
        int newVisibility = header.getDetailVisibility();
        for(LinearLayout row : details){
            row.setVisibility(newVisibility);
        }
    }

}
