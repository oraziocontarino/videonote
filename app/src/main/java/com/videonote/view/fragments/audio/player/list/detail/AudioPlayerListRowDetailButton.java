package com.videonote.view.fragments.audio.player.list.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.videonote.database.dto.NoteDTO;
import com.videonote.R;

public class AudioPlayerListRowDetailButton extends AudioPlayerListRowDetail {
    private TextView header;
    private Button button;
    private Drawable image;
    public AudioPlayerListRowDetailButton(Context context, String label, int resourceImage, NoteDTO note, OnClickListener listener){
        super(context, note);

        image = ContextCompat.getDrawable(getContext(), resourceImage);
        header = generateHeaderColumn("Action", 70);
        button = generateButtonColumn(label, 30);
        button.setOnClickListener(listener);
        super.addView(header);
        super.addView(button);
    }

    private TextView generateHeaderColumn(String titleText, int height){
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

    private Button generateButtonColumn(String label, int weight){
        LayoutParams entryLayout = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                weight
        );

        Button column = new Button(getContext());
        column.setLayoutParams(entryLayout);
        column.setBackgroundResource(R.drawable.table_secondary);
        column.setText(label);
        column.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null);
        column.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryText));
        column.setGravity(Gravity.CENTER);
        return column;
    }
}
