package com.videonote.view.fragments.audio.player;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.dto.NoteDTO;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;

import org.w3c.dom.Text;

public class AudioPlayerList {
    private Fragment fragment;
    private LinearLayout recordsList;
    private Button addTextNoteButton;
    private Button addFileNoteButton;
    private NoteDTO noteDTO;
    private RecordDTO recordDTO;
    private EditText noteText;
    private EditText noteFile;
    private NoteRepository noteRepository;

    public AudioPlayerList(final Fragment fragment){
        this.fragment = fragment;
        recordsList = (LinearLayout) fragment.getView().findViewById(R.id.audioPlayerRecordsContainer);
    }

    public void clean(){

    }
    private Context getContext(){
        return fragment.getContext();
    }

    private TextView generateTitleColumn(String titleText){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        int entryTextColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryText);

        TextView title = new TextView(getContext());
        title.setLayoutParams(entryLayout);
        title.setBackgroundResource(R.drawable.table_button);
        title.setEms(10);
        title.setGravity(Gravity.CENTER);
        title.setText(titleText);
        title.setTextColor(entryTextColor);
        return title;
    }

    private ImageButton generateButtonColumn(int entryImage){
        LinearLayout.LayoutParams entryLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        final ImageButton button = new ImageButton(getContext());
        button.setBackgroundResource(R.drawable.table_button);
        button.setImageResource(entryImage);
        button.setClickable(true);
        return button;
    }

    public void generateRow(String titleText){
        LinearLayout.LayoutParams containerLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(containerLayout);
        container.setOrientation(LinearLayout.HORIZONTAL);

        final TextView title = generateTitleColumn(titleText);
        ImageButton playButton = generateButtonColumn(R.drawable.ic_baseline_play_arrow_24px);
        ImageButton deleteButton = generateButtonColumn(R.drawable.ic_baseline_delete_forever_24px);
        ImageButton moreButton = generateButtonColumn(R.drawable.ic_baseline_unfold_more_24px);
        ImageButton lessButton = generateButtonColumn(R.drawable.ic_baseline_unfold_less_24px);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(fragment.getContext(), "Playing record: "+title.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(fragment.getContext(), "Record deleted"+title.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(fragment.getContext(), "Playing", Toast.LENGTH_SHORT).show();
            }
        });
        lessButton.setVisibility(View.GONE);
        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(fragment.getContext(), "Playing", Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(title);
        container.addView(playButton);
        container.addView(deleteButton);
        container.addView(moreButton);
        container.addView(lessButton);


        recordsList.addView(container);
    }
}

