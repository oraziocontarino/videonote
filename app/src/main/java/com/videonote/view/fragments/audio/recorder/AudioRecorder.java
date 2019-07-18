package com.videonote.view.fragments.audio.recorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.videonote.database.DatabaseManager;
import com.videonote.database.repositories.RecordRepository;
import com.videonote.R;
import com.videonote.utils.FileUtils;
import com.videonote.utils.MediaRecorderHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioRecorder.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioRecorder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioRecorder extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private TextView status;
    private Button startRecordingButton;
    private Button stopRecordingButton;
    private Button pauseRecordingButton;
    private Button resumeRecordingButton;
    private AudioNoteList noteList;
    private RecordRepository recordRepository;
    private MediaRecorderHelper mediaRecorderHelper;
    private String statusLabel;
    private Handler handler;
    private Runnable handlerTask;
    public AudioRecorder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioRecordingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioRecorder newInstance(String param1, String param2
    ) {
        AudioRecorder fragment = new AudioRecorder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
        //mParam1 = getArguments().getString(ARG_PARAM1);
        //mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio_recorder, container, false);
        // Inflate the layout for this fragment
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        cleanFragment();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void cleanFragment(){
        handler.removeCallbacks(handlerTask);
        status.setText("IDLE");
        noteList.clean();
        mediaRecorderHelper.clean();
    }

    private void initFragment(){
        // Init view
        mediaRecorderHelper = MediaRecorderHelper.getInstance(getView().getContext());
        noteList = new AudioNoteList(this);

        // Init Database
        recordRepository = DatabaseManager.getInstance(getContext()).getRecordRepository();

        // Init UI
        status = (TextView) getView().findViewById(R.id.audioRecordStatusValue);
        startRecordingButton = (Button) getView().findViewById(R.id.audioRecordStartButton);
        stopRecordingButton = (Button) getView().findViewById(R.id.audioRecordStopButton);
        pauseRecordingButton = (Button) getView().findViewById(R.id.audioRecordPauseButton);
        resumeRecordingButton = (Button) getView().findViewById(R.id.audioRecordResumeButton);
        updateRecordingButton(true,false,false,false);


        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    noteList.updateRecordDTO(FileUtils.getFilePath(view.getContext(), "audio.3gp", true));
                    mediaRecorderHelper.startAudioRecording(noteList.getRecordDTO());
                    recordRepository.insert(noteList.getRecordDTO());
                    statusLabel = "RECORDING";
                    updateRecordingButton(false,true,true,false);
                    noteList.updateButtons(true);
                }catch(Exception e){
                    //TODO: print error message in toast notification
                    Log.d("AUDIO", e.getMessage() == null ? "null": e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        stopRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: set audio length with info in totalRecordingTime
                MediaRecorderHelper.getInstance(view.getContext()).stopAudioRecording();
                statusLabel = "STOPPED";
                updateRecordingButton(true,false,false,false);
            }
        });

        pauseRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaRecorderHelper.getInstance(view.getContext()).pauseAudioRecording();
                statusLabel = "PAUSED";
                updateRecordingButton(false,true,false,true);
            }
        });

        resumeRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorderHelper.resumeAudioRecording();
                statusLabel = "RECORDING";
                updateRecordingButton(false,true,true,false);
            }
        });

        noteList.updateButtons(false);
        //List<RecordDTO> records = recordRepository.getAllRecordsByType(Common.RECORD_TYPE.AUDIO);


        updateRecordingStatus();
    }

    private void updateRecordingButton(boolean start, boolean stop, boolean pause, boolean resume){
        startRecordingButton.setVisibility(start ? View.VISIBLE : View.GONE);
        stopRecordingButton.setVisibility(stop ? View.VISIBLE : View.GONE);
        pauseRecordingButton.setVisibility(pause ? View.VISIBLE : View.GONE);
        resumeRecordingButton.setVisibility(resume ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart(){
        super.onStart();
        initFragment();
    }


    void updateRecordingStatus(){
        handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
                // do something
                status.setText(mediaRecorderHelper.getFormattedTime()+" - "+statusLabel);
                handler.postDelayed(handlerTask, 100);
            }
        };
        handlerTask.run();
    }
}

