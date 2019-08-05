package com.videonote.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.videonote.NavigationDrawerConstants;
import com.videonote.R;
import com.videonote.view.MainActivity;
import com.videonote.view.PermissionsManager;
import com.videonote.view.fragments.audio.player.AudioPlayerController;
import com.videonote.view.fragments.common.CustomFragment;

public class NoPermissionsFragment extends CustomFragment {
    private static NoPermissionsFragment instance;

    public static NoPermissionsFragment getInstance(){
        if(instance == null){
            instance = new NoPermissionsFragment();
        }
        return instance;
    }

    public NoPermissionsFragment() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_NO_PERMISSIONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_permissions, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        // Required empty public constructor
        Button btn = (Button) getView().findViewById(R.id.requestPermissionsButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsManager.checkAll(getActivity());
            }
        });

    }
    @Override
    public void clean() {

    }
}
