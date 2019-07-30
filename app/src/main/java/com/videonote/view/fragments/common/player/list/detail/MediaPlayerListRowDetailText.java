package com.videonote.view.fragments.common.player.list.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.TextView;

import com.videonote.R;
import com.videonote.database.dto.NoteDTO;

public class MediaPlayerListRowDetailText extends MediaPlayerListRowDetail {
    private TextView header;
    private TextView content;
    public MediaPlayerListRowDetailText(Context context, String headerLabel, String contentLabel, NoteDTO note){
        super(context, note);
        header = generateColumn(headerLabel, 70);
        content = generateColumn(contentLabel, 30);

        super.addView(header);
        super.addView(content);
    }

    private TextView generateColumn(String titleText, int height){
        LayoutParams entryLayout = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                height
        );

        TextView column = new TextView(getContext());
        column.setLayoutParams(entryLayout);
        column.setBackgroundResource(R.drawable.table_secondary);
        column.setEms(10);
        column.setGravity(Gravity.CENTER);
        column.setText(titleText);
        column.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryText));
        return column;
    }
}
