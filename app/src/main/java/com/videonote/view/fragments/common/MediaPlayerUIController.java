package com.videonote.view.fragments.common;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRow;

public abstract class MediaPlayerUIController extends MediaUIController {
    protected Button pauseButton;
    protected Button resumeButton;

    public MediaPlayerUIController(Fragment fragment, int recordId, int statusId, int startButtonId, int stopButtonId, int pauseButtonId, int resumeButtonId){
        super(fragment, recordId, statusId, startButtonId, stopButtonId);
        pauseButton = getView().findViewById(pauseButtonId);
        resumeButton = getView().findViewById(resumeButtonId);
        pauseButtonHandler();
        resumeButtonHandler();
    }

    protected void updateButton(boolean start, boolean stop, boolean pause, boolean resume){
        super.updateButton(start, stop);
        pauseButton.setVisibility(pause ? View.VISIBLE : View.GONE);
        resumeButton.setVisibility(resume ? View.VISIBLE : View.GONE);
    }

    private void pauseButtonHandler(){
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAction();
            }
        });
    }

    private void resumeButtonHandler(){
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeAction();
            }
        });
    }

    public abstract void startAction(RecordDTO record);
    public abstract void startAction();
    public abstract void stopAction();
    public abstract void removeRow(MediaPlayerListRow row);
    protected abstract void pauseAction();
    protected abstract void resumeAction();
    protected abstract void updateHeaderTime();
    protected abstract void hookInterval();



}
