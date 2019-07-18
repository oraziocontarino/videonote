package com.videonote.view;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.videonote.R;
import com.videonote.view.fragments.HomeFragment;
import com.videonote.view.fragments.audio.player.AudioPlayer;
import com.videonote.view.fragments.audio.recorder.AudioRecorder;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AudioRecorder.OnFragmentInteractionListener,
        AudioPlayer.OnFragmentInteractionListener {
    private View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                NoteRepository db = new NoteRepository(MainActivity.this);
                Log.d("MAIN_ACTIVITY", db.getAllDatas().toString());
                long id = db.insert(new NoteDTO("1563107207070_1563107207069_text_note.txt", "30", "MOCK_NONE", "AUDIO"));
                NoteDTO test = db.getData(id);
                Log.d("MAIN_ACTIVITY", "retrived: "+test.toString());
                */

                /*
                Snackbar.make(view, recording ? "Stopping registration" : "Starting registration...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                boolean operation = recording ? stopRecording() : startRecording();
                */
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        //recorder = new MediaRecorder();

        //Select Home by default
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        switch(item.getItemId()){
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_audio_player:
                fragment = new AudioPlayer();
                break;
            case R.id.nav_audio_recorder:
                fragment = new AudioRecorder();
                break;
            case R.id.nav_video_player:
                fragment = new HomeFragment();
                break;
            case R.id.nav_video_recorder:
                fragment = new HomeFragment();
                break;
            case R.id.nav_exit:
                System.exit(0);
                break;
        }
        if(fragment != null) {
            displaySelectedFragment(fragment);
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
