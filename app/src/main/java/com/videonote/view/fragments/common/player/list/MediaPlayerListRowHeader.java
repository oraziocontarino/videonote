package com.videonote.view.fragments.common.player.list;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.view.MainActivity;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.MediaPlayerUIController;

import java.util.List;

public class MediaPlayerListRowHeader extends LinearLayout {
    private LayoutParams layout;
    private TextView title;
    private ImageButton playButton;
    private ImageButton deleteButton;
    private ImageButton moreButton;
    private ImageButton lessButton;
    private MediaPlayerUIController manager;
    private MediaPlayerListRow row;
    private RecordRepository recordRepository;
    private NoteRepository noteRepository;

    //Top UI
    private RecordDTO record;

    public MediaPlayerListRowHeader(MediaPlayerListRow row, MediaPlayerUIController manager, RecordDTO record){
        super(row.getContext());
        this.record = record;
        this.manager = manager;
        this.row = row;

        layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.HORIZONTAL);

        title = generateTitleColumn(record.getFileName(true));
        super.addView(title);

        if(manager != null){
            playButton = generateButtonColumn(R.drawable.ic_baseline_play_arrow_24px);
            deleteButton = generateButtonColumn(R.drawable.ic_baseline_delete_forever_24px);
            super.addView(playButton);
            super.addView(deleteButton);
        }

        moreButton = generateButtonColumn(R.drawable.ic_baseline_unfold_more_24px);
        lessButton = generateButtonColumn(R.drawable.ic_baseline_unfold_less_24px);
        lessButton.setVisibility(View.GONE);
        super.addView(moreButton);
        super.addView(lessButton);
        initPlayerButtons();

        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();
    }

    private void initPlayerButtons(){
        if(manager != null){
            playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        toastPlay();
                        manager.startAction(record);
                    }catch(Exception e){
                        Log.d("MEDIA", "ERROR");
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        manager.stopAction();
                        deleteNotes();
                        deleteRecord();
                        toastDelete();
                        manager.removeRow(row);
                    }catch(Exception e){
                        Log.d("MEDIA", "ERROR");
                    }
                }
            });
        }
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row.toggleDetail();
                toggleDetail();
            }
        });

        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row.toggleDetail();
                toggleDetail();
            }
        });
    }

    public void toastPlay(){
        Toast.makeText(getContext(), "Playing record "+record.getFileName(), Toast.LENGTH_SHORT).show();
    }

    public void toastDelete(){
        Toast.makeText(getContext(), "Deleted record: "+record.getFileName(), Toast.LENGTH_SHORT).show();
    }

    private TextView generateTitleColumn(String titleText){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        int entryTextColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryText);

        TextView title = new TextView(getContext());
        title.setLayoutParams(entryLayout);
        title.setBackgroundResource(R.drawable.table_primary);
        title.setEms(10);
        title.setGravity(Gravity.CENTER);
        title.setText(titleText);
        title.setTextColor(entryTextColor);
        return title;
    }

    private ImageButton generateButtonColumn(int entryImage){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        final ImageButton button = new ImageButton(getContext());
        button.setBackgroundResource(R.drawable.table_primary);
        button.setImageResource(entryImage);
        button.setClickable(true);
        return button;
    }

    public void toggleDetail(){
        toggleRecordDetail(moreButton, lessButton);
    }

    private void toggleRecordDetail(ImageButton more, ImageButton less){
        int tmp = more.getVisibility();
        more.setVisibility(less.getVisibility());
        less.setVisibility(tmp);
    }

    public int getDetailVisibility(){
        return lessButton.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
    }

    private void deleteNotes(){
        // Get notes to delete
        List<NoteDTO> notes = noteRepository.getByRecordId(record.getId());

        // Delete notes from storage
        for(NoteDTO note : notes){
            try {
                FileUtils.deleteFile(getContext(), note.getFileName());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // Delete notes from database
        noteRepository.deleteByRecord(record.getId());
    }
    private void deleteRecord(){
        // Delete notes from storage
        try {
            FileUtils.deleteFile(getContext(), FileUtils.getFileNameFromPath(record.getFileName()));
        }catch(Exception e){
            e.printStackTrace();
        }

        // Delete notes from database
        recordRepository.delete(record);
    }
}
