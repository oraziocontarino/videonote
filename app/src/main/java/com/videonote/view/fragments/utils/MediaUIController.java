package com.videonote.view.fragments.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.videonote.database.dto.RecordDTO;

public abstract class MediaUIController {
    protected Fragment fragment;
    protected View view;
    // UI
    protected TextView status;
    protected TextView recordName;
    protected String statusLabel;
    protected String headerTime;
    protected Handler handler;
    protected Runnable handlerTask;
    protected Button startButton;
    protected Button stopButton;

    public MediaUIController(Fragment fragment, int recordId, int statusId, int startButtonId, int stopButtonId){
        this.fragment = fragment;
        status = (TextView) getView().findViewById(statusId);
        recordName = getView().findViewById(recordId);
        startButton = (Button) getView().findViewById(startButtonId);
        stopButton = (Button) getView().findViewById(stopButtonId);
        statusLabel = "IDLE";
        updateButton(false, false);
        startButtonHandler();
        stopButtonHandler();

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

    protected void updateButton(boolean start, boolean stop){
        startButton.setVisibility(start ? View.VISIBLE : View.GONE);
        stopButton.setVisibility(stop ? View.VISIBLE : View.GONE);
    }

    protected void updateStatus(){
        handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
                updateHeaderTime();
                status.setText(headerTime);
                hookInterval();
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

    protected abstract void startAction(RecordDTO record);
    protected abstract void startAction();
    protected abstract void stopAction();
    protected abstract void updateHeaderTime();
    protected abstract void hookInterval();



}
