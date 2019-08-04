package com.videonote.view.fragments.audio.player;

import android.content.Context;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.MediaPlayerUIController;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRow;

public class AudioPlayerListRow extends MediaPlayerListRow {
    public AudioPlayerListRow(MediaPlayerUIController manager, Context context, RecordDTO recordDTO){
        super(manager, context, recordDTO);
    }
}
