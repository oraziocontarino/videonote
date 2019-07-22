package com.videonote.view.fragments.audio;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.videonote.database.dto.RecordDTO;

public abstract class AudioManager {
    private Fragment fragment;
    private View view;
    // UI
    protected TextView status;
    protected String statusLabel;
    protected String headerTime;
    protected Handler handler;
    protected Runnable handlerTask;
    protected Button startButton;
    protected Button stopButton;
    protected Button pauseButton;
    protected Button resumeButton;

    public AudioManager(Fragment fragment, int statusId, int startButtonId, int stopButtonId, int pauseButtonId, int resumeButtonId){
        this.fragment = fragment;
        status = (TextView) getView().findViewById(statusId);
        startButton = (Button) getView().findViewById(startButtonId);
        stopButton = (Button) getView().findViewById(stopButtonId);
        pauseButton = (Button) getView().findViewById(pauseButtonId);
        resumeButton = (Button) getView().findViewById(resumeButtonId);
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
        startButton.setVisibility(start ? View.VISIBLE : View.GONE);
        stopButton.setVisibility(stop ? View.VISIBLE : View.GONE);
        pauseButton.setVisibility(pause ? View.VISIBLE : View.GONE);
        resumeButton.setVisibility(resume ? View.VISIBLE : View.GONE);
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

    private void startButtonHandler() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAction();
            }
        });
    }
    private void stopButtonHandler(){
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAction();
            }
        });
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



}
