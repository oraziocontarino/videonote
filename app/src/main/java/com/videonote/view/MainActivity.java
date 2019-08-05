package com.videonote.view;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.videonote.R;
import com.videonote.view.fragments.HomeFragment;
import com.videonote.view.fragments.NoPermissionsFragment;
import com.videonote.view.fragments.audio.player.AudioPlayer;
import com.videonote.view.fragments.audio.recorder.AudioRecorder;
import com.videonote.view.fragments.common.CustomFragment;
import com.videonote.view.fragments.dashboard.DashboardFragment;
import com.videonote.view.fragments.video.player.VideoPlayer;
import com.videonote.view.fragments.video.recorder.VideoRecorder;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AudioRecorder.OnFragmentInteractionListener,
        AudioPlayer.OnFragmentInteractionListener,
        VideoRecorder.OnFragmentInteractionListener,
        VideoPlayer.OnFragmentInteractionListener,
        NoPermissionsFragment.OnFragmentInteractionListener {
    private View navHeader;
    private CustomFragment currentFragment;
    private boolean loadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        //Select Home by default
        //navigationView.setCheckedItem(R.id.nav_home);
        loadFragment = false;
        displaySelectedFragment(HomeFragment.getInstance());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadFragment = true;
        // Handle navigation view item clicks here.
        if(currentFragment != null){
            switch(item.getItemId()){
                case R.id.nav_home:
                case R.id.nav_audio_player:
                case R.id.nav_audio_recorder:
                case R.id.nav_video_player:
                case R.id.nav_video_recorder:
                case R.id.nav_exit:
                    currentFragment.clean();
                    break;
            }
        }

        switch(item.getItemId()){
            case R.id.nav_home:
                currentFragment = DashboardFragment.getInstance();
                break;
            case R.id.nav_audio_player:
                currentFragment = AudioPlayer.getInstance();
                break;
            case R.id.nav_audio_recorder:
                currentFragment = AudioRecorder.getInstance();
                break;
            case R.id.nav_video_player:
                currentFragment = VideoPlayer.getInstance();
                break;
            case R.id.nav_video_recorder:
                currentFragment = VideoRecorder.getInstance();
                break;
            case R.id.nav_exit:
                System.exit(0);
                break;
        }
        if(currentFragment != null) {
            PermissionsManager.checkAll(this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the specified fragment to the frame
     *
     * @param fragment
     */
    private void displaySelectedFragment(Fragment fragment) {
        loadFragment = false;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if(!PermissionsManager.checkSuccess(requestCode, permissions, grantResults)){
            displaySelectedFragment(NoPermissionsFragment.getInstance());
            return;
        }
        if(currentFragment == null){
            return;
        }
        if(loadFragment) {
            displaySelectedFragment(currentFragment);
        }
    }
}
