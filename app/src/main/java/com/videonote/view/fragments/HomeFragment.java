package com.videonote.view.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.NavigationDrawerConstants;
import com.videonote.R;
import com.videonote.view.fragments.common.CustomFragment;


public class HomeFragment extends CustomFragment {
    private static HomeFragment instance;

    public static HomeFragment getInstance(){
        if(instance == null){
            instance = new HomeFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_HOME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void clean() {

    }
}
