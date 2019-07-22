package com.videonote.view.fragments.audio.player.list;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.videonote.R;
import com.videonote.database.dto.RecordDTO;
import com.videonote.utils.MediaPlayerManager;
import com.videonote.view.fragments.audio.player.AudioPlayerManager;

public class AudioPlayerListRowHeader extends LinearLayout {
    private LayoutParams layout;
    private TextView title;
    private ImageButton playButton;
    private ImageButton deleteButton;
    private ImageButton moreButton;
    private ImageButton lessButton;
    private AudioPlayerManager manager;
    private AudioPlayerListRow row;

    //Top UI
    private RecordDTO record;

    public AudioPlayerListRowHeader(AudioPlayerListRow row, AudioPlayerManager manager, RecordDTO record){
        super(manager.getContext());
        this.record = record;
        this.manager = manager;
        this.row = row;

        layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        super.setLayoutParams(layout);
        super.setOrientation(LinearLayout.HORIZONTAL);

        title = generateTitleColumn(record.getFileName(true));
        playButton = generateButtonColumn(R.drawable.ic_baseline_play_arrow_24px);
        deleteButton = generateButtonColumn(R.drawable.ic_baseline_delete_forever_24px);
        moreButton = generateButtonColumn(R.drawable.ic_baseline_unfold_more_24px);
        lessButton = generateButtonColumn(R.drawable.ic_baseline_unfold_less_24px);

        lessButton.setVisibility(View.GONE);


        initPlayerButtons();

        super.addView(title);
        super.addView(playButton);
        super.addView(deleteButton);
        super.addView(moreButton);
        super.addView(lessButton);
    }

    private void initPlayerButtons(){
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    toastPlay();
                    manager.startAction(record);
                }catch(Exception e){
                    Log.d("AUDIO", "ERROR");
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.stopAction();
                toastDelete();
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row.toggleDetail();
                toggleDetail();
            }
        });

        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row.toggleDetail();
                toggleDetail();
            }
        });
    }

    public void toastPlay(){
        Toast.makeText(getContext(), "Playing record "+record.getFileName(), Toast.LENGTH_SHORT).show();
    }

    public void toastDelete(){
        Toast.makeText(getContext(), "Deleted record: "+record.getFileName(), Toast.LENGTH_SHORT).show();
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

    public void toggleDetail(){
        toggleRecordDetail(moreButton, lessButton);
    }

    private void toggleRecordDetail(ImageButton more, ImageButton less){
        int tmp = more.getVisibility();
        more.setVisibility(less.getVisibility());
        less.setVisibility(tmp);
    }

    public int getDetailVisibility(){
        return lessButton.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
    }
}
