package com.videonote.view.fragments.video.recorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;

public class VideoRecorder extends Fragment {
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
        mListener = null;
        videoRecorderController.clean();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        videoRecorderController = new VideoRecorderController(this);
        //mCameraDevice = getView().findViewById(R.id.videoCameraPreviewTextureView);
        //mPreviewSize = getView().findViewById(R.id.videoCameraPreviewTextureView);
        //openCamera(VIDEO_WIDTH, VIDEO_HEIGHT);
        //startRecordingVideo();
    }
}
