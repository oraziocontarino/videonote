package com.videonote.view.fragments.video.recorder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;

import com.videonote.Common;
import com.videonote.R;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaPhotoManager;
import com.videonote.view.fragments.audio.AudioMediaRecorderManager;
import com.videonote.view.fragments.common.list.MediaRecorderList;

public class VideoRecorderListManager extends MediaRecorderList {

    public VideoRecorderListManager(Fragment fragment){
        super(fragment, R.id.videoRecordNoteContainer, R.id.videoRecordTextButton, R.id.videoRecordTextInput);
    }
}
