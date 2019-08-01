package com.videonote.view.fragments.video.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;
import com.videonote.view.fragments.common.CustomFragment;
import com.videonote.view.fragments.video.recorder.VideoRecorder;
import com.videonote.view.fragments.video.recorder.VideoRecorderController;

public class VideoPlayer extends CustomFragment {
    private static VideoPlayer instance;
    private VideoPlayerController videoPlayerController;
    public static VideoPlayer getInstance(){
        if(instance == null){
            instance = new VideoPlayer();
        }
        return instance;
    }

    private OnFragmentInteractionListener mListener;

    public VideoPlayer() {
        // Required empty public constructor
    }

    public static VideoPlayer newInstance(String param1, String param2) {
        VideoPlayer fragment = new VideoPlayer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        videoPlayerController = new VideoPlayerController(this);
    }
    @Override
    public void clean() {
        mListener = null;
        if(videoPlayerController !=null) {
            videoPlayerController.clean();
        }
    }
}
