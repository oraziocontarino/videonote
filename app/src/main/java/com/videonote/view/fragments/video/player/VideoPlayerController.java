package com.videonote.view.fragments.video.player;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.utils.FileUtils;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRow;
import com.videonote.view.fragments.common.MediaPlayerUIController;
import com.videonote.view.fragments.video.VideoMediaPlayerManager;

import java.util.List;
import java.util.Stack;

public class VideoPlayerController extends MediaPlayerUIController {
    private Fragment fragment;
    private VideoMediaPlayerManager videoMediaPlayerManager;
    private RecordRepository recordRepository;
    private NoteRepository noteRepository;
    private LinearLayout recordsList;

    private List<NoteDTO> currentRecordNotesList;
    private Stack<NoteDTO> currentRecordNotesStack;
    private NoteDTO nextNote;
    private TextView textAttachment;
    private ScrollView attachments;
    private LinearLayout attachmentsWrapper;

    public VideoPlayerController(Fragment fragment){
        super(fragment, R.id.videoPlayerStatusLabel, R.id.videoPlayerStatusValue, R.id.videoPlayerStartButton, R.id.videoPlayerStopButton, R.id.videoPlayerPauseButton, R.id.videoPlayerResumeButton);
        this.fragment = fragment;

        // Init mediaplayer note
        this.currentRecordNotesList = null;
        this.currentRecordNotesStack = null;
        this.nextNote = null;
        //videoPlayerControllerAsync = new VideoPlayerControllerAsync(this);
        //videoPlayerControllerAsync.execute();
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();


        fragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        // Init recording list

        updateButton(false,false,false,false);
    }

    private void init(){
        videoMediaPlayerManager = VideoMediaPlayerManager.getInstance(fragment);
        noteRepository = DatabaseManager.getInstance(getContext()).getNoteRepository();
        attachments = getView().findViewById(R.id.attachments);
        textAttachment = getView().findViewById(R.id.textAttachment);
        attachmentsWrapper = getView().findViewById(R.id.attachmentsWrapper);
        initRecordsList();
    }

    @Override
    public void startAction(RecordDTO record) {
        try{
            currentRecordNotesList = noteRepository.getByRecordId(record.getId());
            currentRecordNotesStack = new Stack<>();
            for(NoteDTO note : currentRecordNotesList){
                currentRecordNotesStack.push(note);
            }
            startAction();
            videoMediaPlayerManager.startVideoPlayer(record);
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
            videoMediaPlayerManager.stopVideoPlayer();
            statusLabel = "STOPPED";
            updateButton(false,false,false,false);
        }catch (Exception e){
            //...
        }
    }

    @Override
    public void pauseAction() {
        try{
            videoMediaPlayerManager.pauseVideoPlayer();
            statusLabel = "PAUSED";
            updateButton(false,true,false,true);
        }catch (Exception e){
            //...
        }
    }

    @Override
    public void resumeAction() {
        try{
            videoMediaPlayerManager.resumeVideoPlayer();
            statusLabel = "PLAYING";
            updateButton(false,true,true,false);
        }catch (Exception e){
            //...
        }
    }

    @Override
    protected void clean(){
        super.clean();
        videoMediaPlayerManager.clean();
    }

    @Override
    protected void updateHeaderTime(){
        headerTime = videoMediaPlayerManager == null ? "": videoMediaPlayerManager.getFormattedTime()+" - "+statusLabel;
    }

    @Override
    protected void hookInterval(){
        if(videoMediaPlayerManager == null){
            return;
        }

        if(videoMediaPlayerManager.isFinished()){
            nextNote = null;
            textAttachment.setText("");
            attachments.setVisibility(View.GONE);
            textAttachment.setVisibility(View.GONE);
            videoMediaPlayerManager.resetVideoPlayer();
            updateButton(false,false,false,false);
        }

        if(currentRecordNotesStack == null || (currentRecordNotesStack.size() == 0 && nextNote == null)){
            return;
        }
        if(nextNote == null){
            nextNote = currentRecordNotesStack.pop();
        }

        if(nextNote.getStartTime() > videoMediaPlayerManager.getTime()){
            return;
        }

        if(nextNote.getType().equals(Common.NOTE_TYPE.TEXT.name())){
            //Toast.makeText(getContext(), "Update text note!", Toast.LENGTH_SHORT).show();
            String text = FileUtils.readTextFile(fragment, nextNote.getFileName());
            textAttachment.setText(text);
            attachments.setVisibility(View.VISIBLE);
            textAttachment.setVisibility(View.VISIBLE);
            nextNote = null;
        }
        /*
        else if(nextNote.getType().equals(Common.NOTE_TYPE.PICTURE.name())){
            //Toast.makeText(getContext(), "Update picture note!", Toast.LENGTH_SHORT).show();
            Bitmap pictureNote = FileUtils.readPictureFile(fragment, nextNote.getFileName());
            imageAttachment.setImageBitmap(pictureNote);
            attachments.setVisibility(View.VISIBLE);
            textAttachment.setVisibility(View.GONE);
            nextNote = null;
        }
        */
    }
    private void initRecordsList(){
        recordsList = getView().findViewById(R.id.videoPlayerRecordsContainer);
        recordsList.removeAllViews();
        List<RecordDTO> records = recordRepository.getByType(Common.RECORD_TYPE.VIDEO);
        for(final RecordDTO record : records){
            recordsList.addView(new MediaPlayerListRow(this, getContext(), record));
        }
    }

    @Override
    public void removeRow(MediaPlayerListRow row){
        recordsList.removeView(row);
    }

}
