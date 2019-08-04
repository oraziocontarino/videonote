package com.videonote.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionsManager {
    private static String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_PERMISSIONS_ID = 1;

    public static void checkAll(Activity activity){
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS_ID);
    }

    public static boolean checkSuccess(int requestCode,
                        String[] permissions, int[] grantResults){
        switch (requestCode) {
            case REQUEST_PERMISSIONS_ID: {
                if(grantResults.length == 0){
                    return false;
                }
                for(int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Log.e("PERMISSIONS", "["+permissions[i]+"] has no permission!");
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
