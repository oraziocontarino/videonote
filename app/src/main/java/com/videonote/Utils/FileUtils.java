package com.videonote.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {
    public static boolean saveTextFile(Fragment fragment, String fileName, String content){
        try {
            Log.i("FILE_UTILS", "file: "+fileName);
            FileOutputStream fos =  fragment.getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            Writer out = new OutputStreamWriter(fos);
            out.write(content);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String readTextFile(Fragment fragment, String fileName){
        try {
            Log.i("FILE_UTILS", "file: "+getFilePath(fragment.getContext(), fileName, false));
            FileInputStream fis = fragment.getActivity().openFileInput(fileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String row;
            while((row = r.readLine()) != null){
                sb.append(row+"\r\n");
            }
            r.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TESTE", "FILE - false");
            return null;
        }
    }

    public static void deleteFile(Fragment fragment, String fileName){
        fragment.getActivity().deleteFile(fileName);
    }

    public static String getFilePath(Context context, String fileName, boolean unique) {
        if(unique){
            fileName = getUniqueName(fileName);
        }
        final File dir = context.getExternalFilesDir(null);
        String path = (dir == null ? "" : (dir.getAbsolutePath() + "/"))
                + fileName;
        return path;
    }

    public static String getUniqueName(String fileName){
        return System.currentTimeMillis() + "_" + fileName;
    }
}
