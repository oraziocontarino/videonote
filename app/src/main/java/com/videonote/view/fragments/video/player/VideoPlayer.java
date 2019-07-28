package com.videonote.view.fragments.video.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;

public class VideoPlayer extends Fragment {
    // Header UI - common to Recorder
    private VideoPlayerManager videoPlayerManager;

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
        mListener = null;
        if(videoPlayerManager!=null) {
            videoPlayerManager.clean();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        videoPlayerManager = new VideoPlayerManager(this);
    }
}
