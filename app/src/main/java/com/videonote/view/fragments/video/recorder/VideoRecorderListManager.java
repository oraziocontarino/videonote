package com.videonote.view.fragments.video.recorder;

import android.support.v4.app.Fragment;

import com.videonote.R;
import com.videonote.view.fragments.common.recorder.list.MediaRecorderList;
import com.videonote.view.fragments.video.VideoMediaRecorderManager;

public class VideoRecorderListManager extends MediaRecorderList {

    public VideoRecorderListManager(Fragment fragment){
        super(
                fragment,
                VideoMediaRecorderManager.getInstance(fragment),
                R.id.videoRecordNoteContainer,
                R.id.videoRecordTextButton,
                R.id.videoRecordTextInput
        );
    }
}
