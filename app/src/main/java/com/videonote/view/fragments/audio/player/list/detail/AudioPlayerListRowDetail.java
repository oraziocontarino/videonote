package com.videonote.view.fragments.audio.player.list.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.videonote.database.dto.NoteDTO;

public class AudioPlayerListRowDetail extends LinearLayout {
    protected LinearLayout.LayoutParams layout;
    protected NoteDTO note;
    private final static int PADDING = 40;
    public AudioPlayerListRowDetail(Context context, NoteDTO note){
        super(context);
        this.note = note;
        layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                100
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.HORIZONTAL);
        super.setVisibility(View.GONE);
        super.setPadding(PADDING,0, PADDING, 0);
    }
    public AudioPlayerListRowDetail(Context context){
        this(context, null);
    }
}
