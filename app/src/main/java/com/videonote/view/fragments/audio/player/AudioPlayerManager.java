package com.videonote.view.fragments.audio.player;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaPlayerManager;
import com.videonote.view.fragments.audio.AudioManager;
import com.videonote.view.fragments.audio.player.list.AudioPlayerListRow;

import org.w3c.dom.Text;

import java.util.List;

public class AudioPlayerManager extends AudioManager {
    private Fragment fragment;
    private MediaPlayerManager mediaPlayerManager;
    private RecordRepository recordRepository;
    private NoteRepository noteRepository;
    private LinearLayout recordsList;

    private List<NoteDTO> currentRecordNotes;
    private TextView textAttachment;
    private ImageView imageAttachment;
    private NoteDTO currentNote;


    public AudioPlayerManager(Fragment fragment){
        super(fragment, R.id.audioPlayerStatusLabel, R.id.audioPlayerStatusValue, R.id.audioPlayerStartButton, R.id.audioPlayerStopButton, R.id.audioPlayerPauseButton, R.id.audioPlayerResumeButton);
        this.fragment = fragment;
        this.currentNote = null;
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaPlayerManager = MediaPlayerManager.getInstance(getView().getContext());
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();
        textAttachment = getView().findViewById(R.id.textAttachment);
        imageAttachment = getView().findViewById(R.id.imageAttachment);
        // Init recording list
        initRecordsList();

        updateButton(false,false,false,false);
    }




    @Override
    public void startAction(RecordDTO record) {
        try{
            currentRecordNotes = noteRepository.getByRecordId(record.getId());
            mediaPlayerManager.startAudioPlayer(record);
            startAction();
        }catch (Exception e){

        }
    }
    @Override
    public void startAction() {
        statusLabel = "PLAYING";
        updateButton(false, true, true, false);
    }

    @Override
    public void stopAction() {
        try{
            mediaPlayerManager.stopAudioPlayer();
            statusLabel = "STOPPED";
            updateButton(false,false,false,false);
        }catch (Exception e){
        //...
        }
    }

    @Override
    public void pauseAction() {
        try{
            mediaPlayerManager.pauseAudioPlayer();
            statusLabel = "STOPPED";
            updateButton(false,true,false,true);
        }catch (Exception e){
            //...
        }
    }

    @Override
    public void resumeAction() {
        try{
            mediaPlayerManager.resumeAudioPlayer();
            statusLabel = "PLAYING";
            updateButton(false,true,true,false);
        }catch (Exception e){
            //...
        }
    }

    @Override
    protected void clean(){
        super.clean();
        mediaPlayerManager.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = mediaPlayerManager == null ? "": mediaPlayerManager.getFormattedTime()+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){
        NoteDTO selected = null;
        if(currentRecordNotes == null){
            return;
        }
        for(NoteDTO note : currentRecordNotes){
            if(selected == null || note.getStartTime() < selected.getStartTime()){
                selected = note;
            }
            break;
        }

        if(selected == currentNote){
            return;
        }
        currentNote = selected;
        if(currentNote.getType().equals(Common.NOTE_TYPE.TEXT.name())){
            Toast.makeText(getContext(), "Update note top box!", Toast.LENGTH_SHORT).show();
            String text = FileUtils.readTextFile(fragment, currentNote.getFileName());
            textAttachment.setText(text);
        }
    }
    private void initRecordsList(){
        recordsList = getView().findViewById(R.id.audioPlayerRecordsContainer);
        List<RecordDTO> records = recordRepository.getByType(Common.RECORD_TYPE.AUDIO);
        for(final RecordDTO record : records){
            recordsList.addView(new AudioPlayerListRow(this, record));
        }
    }
}
