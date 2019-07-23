package com.videonote.view.fragments.audio.player.list;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.utils.MediaPlayerManager;
import com.videonote.view.fragments.audio.player.AudioPlayerManager;
import com.videonote.view.fragments.audio.player.list.detail.AudioPlayerListRowDetailButton;
import com.videonote.view.fragments.audio.player.list.detail.AudioPlayerListRowDetailDivider;
import com.videonote.view.fragments.audio.player.list.detail.AudioPlayerListRowDetailText;

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
            details.add(new AudioPlayerListRowDetailText(manager.getContext(), "Time", MediaPlayerManager.getFormattedTime(note.getStartTime()), note));

            details.add(new AudioPlayerListRowDetailButton(manager.getContext(), "Delete", R.drawable.ic_baseline_delete_forever_24px, note, new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Delete note evt!", Toast.LENGTH_SHORT).show();
                }
            }));
            details.add(new AudioPlayerListRowDetailDivider(manager.getContext()));
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
