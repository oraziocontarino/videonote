package com.videonote.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.videonote.R;

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
            File myFile = new File(fileName);
            FileOutputStream fos  = new FileOutputStream(myFile);
            FileOutputStream fos =  fragment.getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            String tmp = myFile.getAbsolutePath();
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

    public static Bitmap readPictureFile(Fragment fragment, String fileName){
        Bitmap picture;
        File file = new File(FileUtils.getFilePath(fragment.getContext(), fileName, false));
        FileInputStream fis = null;
        try{
            //FileInputStream fis = fragment.getActivity().openFileInput(fileName);
            fis = new FileInputStream(file);
            picture = BitmapFactory.decodeStream(fis);
        }catch(Exception e){
            picture = null;
        } finally {
            //Always clear and close
            try {
                fis.close();
            } catch (IOException e) {
            }
        }
        return picture;
    }

    public static void deleteFile(Context context, String fileName){
        File file = new File(FileUtils.getFilePath(context, fileName, false));
        if(file.exists()){
            file.delete();
        }
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

    public static String getFileNameFromPath(String path){
        String[] pieces = path.split("/");
        return pieces[pieces.length - 1];
    }
}
