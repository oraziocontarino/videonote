package com.videonote.view.fragments.audio;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.videonote.R;
import com.videonote.utils.MediaRecorderHelper;

public abstract class AudioManager {
    private Fragment fragment;
    private View view;
    // UI
    protected TextView status;
    protected String statusLabel;
    protected String headerTime;
    protected Handler handler;
    protected Runnable handlerTask;
    protected Button startRecordingButton;
    protected Button stopRecordingButton;
    protected Button pauseRecordingButton;
    protected Button resumeRecordingButton;

    public AudioManager(Fragment fragment, int statusId, int startButtonId, int stopButtonId, int pauseButtonId, int resumeButtonId){
        this.fragment = fragment;
        status = (TextView) getView().findViewById(statusId);
        startRecordingButton = (Button) getView().findViewById(startButtonId);
        stopRecordingButton = (Button) getView().findViewById(stopButtonId);
        pauseRecordingButton = (Button) getView().findViewById(pauseButtonId);
        resumeRecordingButton = (Button) getView().findViewById(resumeButtonId);
        statusLabel = "IDLE";

        startButtonHandler();
        stopButtonHandler();
        pauseButtonHandler();
        resumeButtonHandler();

        // Start time updater
        updateStatus();
    }

    protected void clean(){
        handler.removeCallbacks(handlerTask);
        status.setText("IDLE");
    }

    public View getView(){
        return fragment.getView();
    }

    public Context getContext(){
        return fragment.getContext();
    }

    protected void updateButton(boolean start, boolean stop, boolean pause, boolean resume){
        startRecordingButton.setVisibility(start ? View.VISIBLE : View.GONE);
        stopRecordingButton.setVisibility(stop ? View.VISIBLE : View.GONE);
        pauseRecordingButton.setVisibility(pause ? View.VISIBLE : View.GONE);
        resumeRecordingButton.setVisibility(resume ? View.VISIBLE : View.GONE);
    }

    protected void updateStatus(){
        handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
                updateHeaderTime();
                status.setText(headerTime);
                handler.postDelayed(handlerTask, 100);
            }
        };
        handlerTask.run();
    }

    protected abstract void startButtonHandler();
    protected abstract void stopButtonHandler();
    protected abstract void pauseButtonHandler();
    protected abstract void resumeButtonHandler();
    protected abstract void updateHeaderTime();
}
