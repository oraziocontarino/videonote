package com.videonote.view.fragments.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.VideoView;

import com.videonote.R;
import com.videonote.database.dto.RecordDTO;
import com.videonote.utils.TimeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VideoMediaPlayerManager {
    private static VideoMediaPlayerManager instance = null;
    private MediaPlayer mediaPlayer;
    private Fragment fragment;
    private boolean dirty;
    private boolean paused;
    private TextureView textureView;
    private Surface surfaceView;
    private boolean finished;
    private RecordDTO record;
    private VideoMediaPlayerManager(Fragment fragment){
        this.fragment = fragment;
        mediaPlayer = new MediaPlayer();
        dirty = false;
    }


    public static VideoMediaPlayerManager getInstance(Fragment fragment){
        if(instance == null){
            instance = new VideoMediaPlayerManager(fragment);
        }else{
            instance.setFragment(fragment);
        }
        return instance;
    }
    private void setFragment(Fragment fragment){
        this.fragment = fragment;
    }
    public void startVideoPlayer(RecordDTO record) throws Exception{
        this.record = record;
        textureView = getView().findViewById(R.id.textureView);
        if(textureView.isAvailable()){
            initializeSurface(textureView.getSurfaceTexture());
        }else{
            initializeSurface();
        }
    }
    private void initializeSurface(){
        textureView.setVisibility(View.VISIBLE);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                initializeSurface(surfaceTexture);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                Log.d("aa","");}

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                Log.d("aa","");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                Log.d("aa","");
            }
        });
    }

    private void initializeSurface(SurfaceTexture surfaceTexture){
        textureView.setVisibility(View.VISIBLE);
        try {
            //destoryIntroVideo();
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }
            surfaceView = new Surface(surfaceTexture);
            mediaPlayer.setSurface(surfaceView);
            mediaPlayer.setDataSource(record.getFileName());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    finished=true;
                }
            });
            dirty = true;
            finished = false;
            //mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        } catch (Exception e) {
            System.err.println("Error playing intro video: " + e.getMessage());
        }
    }
    public void pauseVideoPlayer(){
        mediaPlayer.pause();
        paused = true;
    }

    public void resumeVideoPlayer(){
        mediaPlayer.setSurface(surfaceView);
        mediaPlayer.start();
        paused = false;
    }
    public void stopVideoPlayer(){
        mediaPlayer.stop();
        mediaPlayer.reset();   // You can reuse the object by going back to setVideoSource() step
        dirty = false;
        paused = false;
        textureView.setVisibility(View.GONE);
    }
    public void resetVideoPlayer(){
        mediaPlayer.reset();   // You can reuse the object by going back to setVideoSource() step
        dirty = false;
        paused = false;
    }
    public boolean isFinished(){
        return finished;
    }
    public boolean isDirty(){
        return dirty;
    }
    public boolean isPaused(){
        return paused;
    }
    public void destroy(){
        mediaPlayer.release(); // Now the object cannot be reused
    }

    public long getTime(){
        return mediaPlayer.getCurrentPosition();
    }

    public String getFormattedTime(){
        long millis = mediaPlayer.getCurrentPosition();
        return TimeUtils.getFormattedTime(millis);
    }

    public void clean(){
        if(mediaPlayer.getCurrentPosition() != 0L){
            if(mediaPlayer.isPlaying()) {
                stopVideoPlayer();
            }else{
                mediaPlayer.reset();
            }
        }
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    private Context getContext(){
        return fragment.getContext();
    }
    private View getView(){
        return fragment.getView();
    }

}
