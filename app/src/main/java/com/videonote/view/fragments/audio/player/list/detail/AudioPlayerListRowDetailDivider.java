package com.videonote.view.fragments.audio.player.list.detail;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.videonote.R;
import com.videonote.database.dto.NoteDTO;

public class AudioPlayerListRowDetailDivider extends AudioPlayerListRowDetail{
    public AudioPlayerListRowDetailDivider(Context context){
        super(context);
        super.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
        super.setMinimumHeight(25);
    }
}
