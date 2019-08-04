package com.videonote.view.fragments.video.player;

import android.content.Context;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.MediaPlayerUIController;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRow;

public class VideoPlayerListRow extends MediaPlayerListRow {
    //private MediaPlayerController manager;
    public VideoPlayerListRow(MediaPlayerUIController manager, Context context, RecordDTO recordDTO){
        super(manager, context, recordDTO);
    }
}
