package com.videonote.view.fragments.utils;

import android.support.v4.app.Fragment;

import com.videonote.database.dto.RecordDTO;

public abstract class MediaRecorderUIController extends MediaUIController {
    public MediaRecorderUIController(Fragment fragment, int recordId, int statusId, int startButtonId, int stopButtonId){
        super(fragment, recordId, statusId, startButtonId, stopButtonId);
    }

    protected abstract void startAction(RecordDTO record);
    protected abstract void startAction();
    protected abstract void stopAction();
    protected abstract void updateHeaderTime();
    protected abstract void hookInterval();
}
