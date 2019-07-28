package com.videonote.view.fragments.utils;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.videonote.database.dto.RecordDTO;

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

    protected abstract void startAction(RecordDTO record);
    protected abstract void startAction();
    protected abstract void stopAction();
    protected abstract void pauseAction();
    protected abstract void resumeAction();
    protected abstract void updateHeaderTime();
    protected abstract void hookInterval();



}
