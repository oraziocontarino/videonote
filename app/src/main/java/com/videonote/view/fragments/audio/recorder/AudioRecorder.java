package com.videonote.view.fragments.audio.recorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;

public class AudioRecorder extends Fragment{
    private AudioRecorderManager audioRecorderManager;

    // Body
    private OnFragmentInteractionListener mListener;
    public AudioRecorder() {
        // Required empty public constructor
    }
    public static AudioRecorder newInstance(String param1, String param2
    ) {
        AudioRecorder fragment = new AudioRecorder();
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
        return inflater.inflate(R.layout.fragment_audio_recorder, container, false);
        // Inflate the layout for this fragment
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
        audioRecorderManager.clean();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        audioRecorderManager = new AudioRecorderManager(this);
    }
}

