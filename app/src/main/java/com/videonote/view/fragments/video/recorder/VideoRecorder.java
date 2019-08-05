package com.videonote.view.fragments.video.recorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.NavigationDrawerConstants;
import com.videonote.R;
import com.videonote.view.fragments.HomeFragment;
import com.videonote.view.fragments.common.CustomFragment;

public class VideoRecorder extends CustomFragment {
    private static VideoRecorder instance;

    public static VideoRecorder getInstance(){
        if(instance == null){
            instance = new VideoRecorder();
        }
        return instance;
    }

    private VideoRecorderController videoRecorderController;
    private OnFragmentInteractionListener mListener;

    public VideoRecorder() {
        // Required empty public constructor
    }

    public static VideoRecorder newInstance(String param1, String param2) {
        VideoRecorder fragment = new VideoRecorder();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_VIDEO_RECORDER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_recorder, container, false);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        videoRecorderController = new VideoRecorderController(this);
    }

    @Override
    public void clean() {
        mListener = null;
        if(videoRecorderController != null) {
            videoRecorderController.clean();
        }
    }
}
