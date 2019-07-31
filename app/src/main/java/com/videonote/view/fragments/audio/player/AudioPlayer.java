package com.videonote.view.fragments.audio.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;
import com.videonote.view.fragments.video.player.VideoPlayer;

public class AudioPlayer extends Fragment {
    private static AudioPlayer instance;

    public static AudioPlayer getInstance(){
        if(instance == null){
            instance = new AudioPlayer();
        }
        return instance;
    }

    // Header UI - common to Recorder
    private AudioPlayerController audioPlayerManager;

    private OnFragmentInteractionListener mListener;

    public AudioPlayer() {
        // Required empty public constructor
    }

    public static AudioPlayer newInstance(String param1, String param2) {
        AudioPlayer fragment = new AudioPlayer();
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
        return inflater.inflate(R.layout.fragment_audio_player, container, false);
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
        if(audioPlayerManager!=null) {
            audioPlayerManager.clean();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        audioPlayerManager = new AudioPlayerController(this);
    }
}
