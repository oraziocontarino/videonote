package com.videonote.view.fragments.dashboard;

import android.content.Context;

import com.videonote.database.dto.RecordDTO;
import com.videonote.view.fragments.common.MediaPlayerUIController;
import com.videonote.view.fragments.common.player.list.MediaPlayerListRow;

public class DashboardPlayerListRow extends MediaPlayerListRow {
    public DashboardPlayerListRow(Context context, RecordDTO recordDTO){
        super(null, context, recordDTO);
    }
}
