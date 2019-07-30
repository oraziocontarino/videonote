package com.videonote.view.fragments.common.player.list.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.videonote.R;

public class MediaPlayerListRowDetailDivider extends MediaPlayerListRowDetail {
    public MediaPlayerListRowDetailDivider(Context context){
        super(context);
        super.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
        super.setMinimumHeight(25);
    }
}
