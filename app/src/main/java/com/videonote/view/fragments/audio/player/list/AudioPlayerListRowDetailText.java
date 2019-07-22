package com.videonote.view.fragments.audio.player.list;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;

public class AudioPlayerListRowDetailText extends LinearLayout {
    private LayoutParams layout;
    private TextView header;
    private TextView content;
    private NoteDTO note;
    public AudioPlayerListRowDetailText(Context context, String headerLabel, String contentLabel, NoteDTO note){
        super(context);
        this.note = note;
        layout = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.HORIZONTAL);
        super.setVisibility(View.GONE);

        header = generateColumn(headerLabel);
        content = generateColumn(contentLabel);


        super.addView(header);
        super.addView(content);
    }

    private TextView generateColumn(String titleText){
        LayoutParams entryLayout = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                1
        );
        int entryTextColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryText);

        TextView column = new TextView(getContext());
        column.setLayoutParams(entryLayout);
        column.setBackgroundResource(R.drawable.table_button);
        column.setEms(10);
        column.setGravity(Gravity.CENTER);
        column.setText(titleText);
        column.setTextColor(entryTextColor);
        return column;
    }
}
