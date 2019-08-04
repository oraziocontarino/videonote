package com.videonote.view.fragments.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videonote.NavigationDrawerConstants;
import com.videonote.R;
import com.videonote.view.fragments.common.CustomFragment;


public class DashboardFragment extends CustomFragment {
    private static DashboardFragment instance;
    private OnFragmentInteractionListener mListener;
    private MapManger mapManager;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment getInstance(){
        if(instance == null){
            instance = new DashboardFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_HOME);
    }

    @Override
    public void clean() {

    }

    @Override
    public void onStart(){
        super.onStart();
        mapManager = new MapManger(this);
    }
}
