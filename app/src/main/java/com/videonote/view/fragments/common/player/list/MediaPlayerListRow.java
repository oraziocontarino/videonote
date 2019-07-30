package com.videonote.view.fragments.common.player.list;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.utils.TimeUtils;
import com.videonote.view.fragments.audio.AudioMediaPlayerManager;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.MediaPlayerUIController;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRowHeader;
import com.videonote.view.fragments.common.player.list.detail.MediaPlayerListRowDetailButton;
import com.videonote.view.fragments.common.player.list.detail.MediaPlayerListRowDetailDivider;
import com.videonote.view.fragments.common.player.list.detail.MediaPlayerListRowDetailText;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayerListRow extends LinearLayout {
    private LayoutParams layout;
    private MediaPlayerListRowHeader header;
    private List<LinearLayout> details;
    private NoteRepository noteRepository;
    private Context context;
    private MediaPlayerUIController manager;
    public MediaPlayerListRow(MediaPlayerUIController manager, Context context, RecordDTO recordDTO){
        super(context);
        this.manager = manager;
        this.context = context;

        layout = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.VERTICAL);
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();

        prepareHeader(manager, recordDTO);
        prepareDetail(recordDTO);
        setRow();
    }

    private void prepareHeader(MediaPlayerUIController manager, RecordDTO recordDTO){
        header = new MediaPlayerListRowHeader(this, manager, recordDTO);
    }
    private void setRow(){
        super.addView(header);
        for(LinearLayout row : details){
            super.addView(row);
        }
    }

    private void prepareDetail(RecordDTO recordDTO){
        List<NoteDTO> notes = noteRepository.getByRecordId(recordDTO.getId());
        details = new ArrayList();
        for(NoteDTO note : notes){
            details.add(new MediaPlayerListRowDetailText(context, "Description", note.getFileName(), note));
            details.add(new MediaPlayerListRowDetailText(context, "Time", TimeUtils.getFormattedTime(note.getStartTime()), note));

            details.add(new MediaPlayerListRowDetailButton(context, "Delete", R.drawable.ic_baseline_delete_forever_24px, note, new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Delete note evt!", Toast.LENGTH_SHORT).show();
                }
            }));
            details.add(new MediaPlayerListRowDetailDivider(context));
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
