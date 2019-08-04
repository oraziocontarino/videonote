package com.videonote.view.fragments.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.videonote.R;
import com.videonote.database.DatabaseManager;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.view.Common;
import com.videonote.view.PermissionsManager;
import com.videonote.view.fragments.video.player.VideoPlayerListRow;

import java.io.IOException;
import java.security.Security;
import java.util.List;
import java.util.Locale;

public class MapManger {
    private Fragment fragment;
    private RecordRepository recordRepository;
    private LinearLayout recordsList;

    public MapManger(Fragment fragment){
        this.fragment = fragment;
        fragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void init(){
        initData();
        initMap();
    }



    private void initData(){
        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();
        initRecordsList();
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) fragment.getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(37.4219999,-122.0862462))
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                addMarker(mMap, "SpiderMan", 37.4219999, -122.0862462);
                addMarker(mMap, "IronMan", 37.4629101, -122.2449094);
                addMarker(mMap, "CaptainAmerica", 37.3092293, -122.1136845);
            }
        });
    }


    private void addMarker(GoogleMap mMap, String label, double lat, double lng){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title(label));
    }

    private void initRecordsList(){
        recordsList = getView().findViewById(R.id.dashboardRecordsContainer);
        recordsList.removeAllViews();
        List<RecordDTO> records = recordRepository.getAll();
        for(final RecordDTO record : records){
            recordsList.addView(new DashboardPlayerListRow(getContext(), record));
        }
    }




    private View getView(){
        return fragment.getView();
    }
    private Activity getActivity(){
        return fragment.getActivity();
    }
    private Context getContext(){
        return fragment.getContext();
    }

}
