package com.wordpress.httpstheredefiningproductions.phonefinder;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import android.view.View;



public class finder extends Activity  {


//used to unmute the phone's audio system
public static AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finder);

        //nothing to do here except set the content view


    }



    //start the service by clicking the "start" button
    public void onClickStartService(View V)
    {



        //used to start the recorder intent this will allow the app to continually listen for the phrase

        Intent i = new Intent(this, recorder.class);

        startService(i);




    }


    //Stop the started service with this code
    public void onClickStopService(View V)
    {
        //activate the audiomanager in order to control the audio of the system
         mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Stop the running service from here
         stopService(new Intent(this, recorder.class));
//unmutes any sound that might have been muted in the process of this application
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false  );
        mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
    }

//when the app is returned from after being put in the background
    @Override
    public void onResume(){
        super.onResume();
       //we want to stop app because when the user goes to app, most likely they will want to stop app
         stopService(new Intent(this, recorder.class));



    }
}