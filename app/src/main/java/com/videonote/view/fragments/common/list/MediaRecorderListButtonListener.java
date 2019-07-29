package com.videonote.view.fragments.common.list;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.videonote.utils.FileUtils;

public class MediaRecorderListButtonListener implements OnClickListener{
    private Fragment fragment;
    private LinearLayout wrapper;
    private LinearLayout noteList;
    private String toDelete;

    // = new NoteDTO();
    public MediaRecorderListButtonListener(Fragment fragment, LinearLayout wrapper, LinearLayout noteList, String toDelete){
        this.fragment = fragment;
        this.wrapper = wrapper;
        this.noteList = noteList;
        this.toDelete = toDelete;
    }

    @Override
    public void onClick(View v) {
        Log.d("AUDIO", "BUTTON PRESSED!");
        FileUtils.deleteFile(getContext(), toDelete);
        noteList.removeView(wrapper);
    }

    private Context getContext(){
        return fragment.getContext();
    }
}
