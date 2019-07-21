package com.videonote.view.fragments.audio.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.R;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioPlayer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioPlayer extends Fragment {
    // Header UI - common to Recorder
    private AudioPlayerManager audioPlayerManager;

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
        audioPlayerManager.clean();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        audioPlayerManager = new AudioPlayerManager(this);
    }
}
