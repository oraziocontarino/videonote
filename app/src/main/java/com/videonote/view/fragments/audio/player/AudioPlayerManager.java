package com.videonote.view.fragments.audio.player;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import java.util.Stack;

public class AudioPlayerManager extends AudioManager {
    private Fragment fragment;
    private MediaPlayerManager mediaPlayerManager;
    private RecordRepository recordRepository;
    private NoteRepository noteRepository;
    private LinearLayout recordsList;

    private List<NoteDTO> currentRecordNotesList;
    private Stack<NoteDTO> currentRecordNotesStack;
    private NoteDTO nextNote;
    private TextView textAttachment;
    private ImageView imageAttachment;
    private ScrollView attachments;
    private LinearLayout attachmentsWrapper;


    public AudioPlayerManager(Fragment fragment){
        super(fragment, R.id.audioPlayerStatusLabel, R.id.audioPlayerStatusValue, R.id.audioPlayerStartButton, R.id.audioPlayerStopButton, R.id.audioPlayerPauseButton, R.id.audioPlayerResumeButton);
        this.fragment = fragment;

        // Init mediaplayer note
        this.currentRecordNotesList = null;
        this.currentRecordNotesStack = null;
        this.nextNote = null;

        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        mediaPlayerManager = MediaPlayerManager.getInstance(getView().getContext());
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();
        attachments = getView().findViewById(R.id.attachments);
        textAttachment = getView().findViewById(R.id.textAttachment);
        imageAttachment = getView().findViewById(R.id.imageAttachment);
        attachmentsWrapper = getView().findViewById(R.id.attachmentsWrapper);

        // Init recording list
        initRecordsList();

        updateButton(false,false,false,false);
    }




    @Override
    public void startAction(RecordDTO record) {
        try{
            currentRecordNotesList = noteRepository.getByRecordId(record.getId());
            currentRecordNotesStack = new Stack<>();
            for(NoteDTO note : currentRecordNotesList){
                currentRecordNotesStack.push(note);
            }
            mediaPlayerManager.startAudioPlayer(record);
            startAction();
        }catch (Exception e){
            Log.e("AUDIO", e.getMessage());
            e.printStackTrace();
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
            statusLabel = "PAUSED";
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
        if(mediaPlayerManager == null){
            return;
        }

        if(!mediaPlayerManager.isPlaying() && !mediaPlayerManager.isPaused() && mediaPlayerManager.isDirty()){
            nextNote = null;
            textAttachment.setText("");
            attachments.setVisibility(View.GONE);
            textAttachment.setVisibility(View.GONE);
            mediaPlayerManager.resetAudioPlayer();
            updateButton(false,false,false,false);
        }

        if(currentRecordNotesStack == null || (currentRecordNotesStack.size() == 0 && nextNote == null)){
            return;
        }
        if(nextNote == null){
            nextNote = currentRecordNotesStack.pop();
        }

        if(nextNote.getStartTime() > mediaPlayerManager.getTime()){
            return;
        }

        if(nextNote.getType().equals(Common.NOTE_TYPE.TEXT.name())){
            Toast.makeText(getContext(), "Update text note!", Toast.LENGTH_SHORT).show();
            String text = FileUtils.readTextFile(fragment, nextNote.getFileName());
            //textAttachment.setText(text);
            //attachments.setVisibility(View.VISIBLE);
            //imageAttachment.setVisibility(View.GONE);
            //textAttachment.setVisibility(View.VISIBLE);
            nextNote = null;
        }else if(nextNote.getType().equals(Common.NOTE_TYPE.PICTURE.name())){
            noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();
            Toast.makeText(getContext(), "Update picture note!", Toast.LENGTH_SHORT).show();
            Bitmap pictureNote = FileUtils.readPictureFile(fragment, nextNote.getFileName());
            imageAttachment.setImageBitmap(pictureNote);
            attachments.setVisibility(View.VISIBLE);
            imageAttachment.setVisibility(View.VISIBLE);
            textAttachment.setVisibility(View.GONE);
            nextNote = null;
        }
    }
    private void initRecordsList(){
        recordsList = getView().findViewById(R.id.audioPlayerRecordsContainer);
        List<RecordDTO> records = recordRepository.getByType(Common.RECORD_TYPE.AUDIO);
        for(final RecordDTO record : records){
            recordsList.addView(new AudioPlayerListRow(this, record));
        }
    }

    public void removeRow(AudioPlayerListRow row){
        recordsList.removeView(row);
    }
}
