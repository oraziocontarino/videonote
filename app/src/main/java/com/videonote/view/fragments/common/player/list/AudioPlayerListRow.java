package com.videonote.view.fragments.common.player.list;

import android.content.Context;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.MediaPlayerUIController;

public class AudioPlayerListRow extends MediaPlayerListRow {
    private AudioPlayerController manager;
    //private MediaPlayerController manager;
    public AudioPlayerListRow(MediaPlayerUIController manager, Context context, RecordDTO recordDTO){
        super(manager, context, recordDTO);
        //AudioPlayerController manager
    }
}
