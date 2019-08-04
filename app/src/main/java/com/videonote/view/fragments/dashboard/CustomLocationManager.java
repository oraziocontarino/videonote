package com.videonote.view.fragments.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.videonote.view.Common;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CustomLocationManager /*implements LocationListener*/ {
    private static CustomLocationManager instance;
    private Context context;
    private final static int MIN_TIME_BW_UPDATES = 5000;
    private final static int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private LocationManager locationManager;


    private CustomLocationManager(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static CustomLocationManager getInstance(Context context) {
        if(instance == null){
            instance = new CustomLocationManager(context);
        }
        return instance;
    }

    private boolean isGPSEnabled(){
        return locationManager
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }
    private boolean isNetworkGPSEnabled(){
        return locationManager
                .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private Location getLocationByNetwork(){
        Location location = null;
        /*
        locationManager.requestLocationUpdates(
                android.location.LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        */
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }

    @SuppressLint("MissingPermission")
    private Location getLocationByGPS(){
        Location location = null;
        /*
        locationManager.requestLocationUpdates(
                android.location.LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        */
        if (locationManager != null) {
            location = locationManager
                    .getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
        }
        return location;
    }

    public Location getLocation() {
        boolean isGPSEnabled;
        boolean isNetworkEnabled;
        Location location = null;
        try {
            // getting GPS status
            isGPSEnabled = isGPSEnabled();

            // getting network status
            isNetworkEnabled = isNetworkGPSEnabled();

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                return location;
            }

            // First get location from GPS Provider
            if (isGPSEnabled) {
                location = getLocationByGPS();
            }

            // if GPS fails get lat/long using GPS Services
            if (location == null && isNetworkEnabled) {
                location = getLocationByNetwork();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
/*
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(
                getContext(),
                "Location changed: Lat: " + location.getLatitude() + " Lng: "
                        + location.getLongitude(), Toast.LENGTH_SHORT).show();
        String latitude = "Latitude: " + location.getLatitude();
        String longitude = "Longitude: " + location.getLongitude();
        String city = Common.geodecodeLocation(getContext(), location.getLatitude(), location.getLongitude());
        System.out.println("data: "+latitude+longitude+city);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
*/


    public String geodecodeLocation(Context context, double latitude, double longitude){

        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() == 0) {
                return null;
            }
            return addresses.get(0).getLocality();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String geodecodeLocation(Context context, Location location){
        return geodecodeLocation(context, location.getLatitude(), location.getLongitude());
    }

    private Context getContext(){
        return this.context;
    }
}
