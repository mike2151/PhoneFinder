package com.wordpress.httpstheredefiningproductions.phonefinder;

/**
 * Created by Michael on 11/14/2014.
 */
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;

import android.support.v4.app.NotificationCompat;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class recorder extends Service
{
//this will allow us to continuously listen even when the phone is on sleep
    public static PowerManager mgr;
//this is for the notification in the top bar
    private static int FOREGROUND_ID=1338;



    //methods
//this allows us to make a notification in the top bar
    private Notification buildForegroundNotification(String filename) {
        //put the app itself in the bar
        NotificationCompat.Builder b=new NotificationCompat.Builder(this);
        //when the icon is clicked  it goes to the screen
        Intent resultIntent = new Intent(this, finder.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
//keeps the notification going on
        b.setOngoing(true);
//some details about the notification such as name and icon shown
        b.setContentTitle(getString(R.string.working))
                .setContentText(filename)
                .setSmallIcon(R.drawable.phonefinder)
                .setTicker(getString(R.string.working));
        b.setContentIntent(resultPendingIntent);

        //finally building the notification
        return(b.build());
    }

    //bunch of declaratory stuff
//used for controlling sound
    static public AudioManager mAudioManager;
    //used for speech input
    protected SpeechRecognizer mSpeechRecognizer;
    //an intent once the data has been received
    protected Intent mSpeechRecognizerIntent;
    //used for phone vibrations
    static public Vibrator v;
    //telling the app how to handle certain messages from the user input
    protected final Messenger mServerMessenger = new Messenger(new IncomingHandler(this));
    //a true or false value to allow us to understand if the app is currently listening
    protected boolean mIsListening;
    //there is a timer that indicated how long the app listens for this boolean lets the app know if this period is in place
    protected volatile boolean mIsCountDownOn;


//used for message processing
    static final int MSG_RECOGNIZER_START_LISTENING = 1;
    static final int MSG_RECOGNIZER_CANCEL = 2;



    @Override
    public void onCreate()
    {
        super.onCreate();
        //get the things above linked up to actual things in the app
        v =  (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());


    }



    protected static class IncomingHandler extends Handler
    {
        private WeakReference<recorder> mtarget;

        IncomingHandler(recorder target)
        {
            mtarget = new WeakReference<recorder>(target);
        }

//message handling
        @Override
        public void handleMessage(Message msg)
        {
            final recorder target = mtarget.get();

            switch (msg.what)
            {
                case MSG_RECOGNIZER_START_LISTENING:

                    //if nothing is currently happening...
                if (!target.mIsListening)
                {

                //start listening for inut
                    target.mSpeechRecognizer.startListening(target.mSpeechRecognizerIntent);

                    //now the app knows it is listening
                    target.mIsListening = true;

                    //this entire chunk of code is used for temporally muting the phone so it does not emit the annoying beep
                    //can be placed in a for or while loop
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER, +AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
                    mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,  + AudioManager.FLAG_PLAY_SOUND);
//end of lowering



                }

                break;
            //if the message got interupted
                case MSG_RECOGNIZER_CANCEL:
                    //dont listen
                        target.mSpeechRecognizer.cancel();
                    //not listening
                        target.mIsListening = false;
                    //unmuting any mute that could have occured
                        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false  );
                        mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);
                    //wait one second
                        try {
                            Thread.sleep(( 1000));
                        } catch (InterruptedException e) {
                    }
                    break;
            }
        }
    }


//timer to wait during input
    protected CountDownTimer mNoSpeechCountDown = new CountDownTimer(5000, 5000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
            // TODO Auto-generated method stub


        }
//once message is done recording
        @Override
        public void onFinish()
        {
            //stop listening
            mIsCountDownOn = false;
            //get the actual message
            Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
            try
            {
                //communicate the message outside of this method to incominghandler
                mServerMessenger.send(message);
                message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
                mServerMessenger.send(message);


            }
            catch (RemoteException e)
            {

            }

        }
    };
//this is when the start button in the activity_finder is pressed
    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        //initialize the thing that will keep the service running
        mgr = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        //turn on the wakelock
        PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire();
//start the notification
        startForeground(FOREGROUND_ID,
                buildForegroundNotification("Phone Finder"));
        try
        {
            //start the listening
            Message msg = new Message();
            msg.what = MSG_RECOGNIZER_START_LISTENING;
            mServerMessenger.send(msg);
        }
        catch (RemoteException e)
        {

        }
        //keep running the service:
        return  START_STICKY;
    }



//when the service gets stopped:
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //get rid of any mute
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false );
        //stop all listening
        if (mIsCountDownOn)
        {
            mNoSpeechCountDown.cancel();
        }
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
    }
//time for understanding message:
    protected class SpeechRecognitionListener implements RecognitionListener
    {
        //sound pool for the beeping
        SoundPool sp;
        //used for making sure the sound pool is ready
        int sound=0;
//once the person starts talking
        @Override
        public void onBeginningOfSpeech()
        {
            //audiomanager reset
            mAudioManager = null;
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            //tell the app not to record while listening
            if (mIsCountDownOn)
            {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }


        }
//once information is being recieved:
        @Override
        public void onBufferReceived(byte[] buffer)
        {


        }
//once the source of input has stopped talking
        @Override
        public void onEndOfSpeech()
        {
            //reset audiomanager; probably not necessary
            mAudioManager = null;
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        }
//if either the app does not understand what you are saying or nothing has been said: (this will be called the most)
        @Override
        public void onError(int error)
        {
            //reset audio manager
            mAudioManager = null;
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            if (mIsCountDownOn)
            {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }


            Message message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
            try
            {
                mIsListening = false;
                mServerMessenger.send(message);

                mAudioManager.setStreamSolo(AudioManager.STREAM_MUSIC, false);


            }
            catch (RemoteException e)
            {

            }




        }
//not important for this application
        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }
//not important
        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }
//ready to start listening
        @Override
        public void onReadyForSpeech(Bundle params)
        {

            if (Build.VERSION.SDK_INT >= 16);//Build.VERSION_CODES.JELLY_BEAN)
            {
                //start listening
                mIsCountDownOn = true;
                mNoSpeechCountDown.start();


            }


        }
//when we get actual words from input
        @Override
        public void onResults(Bundle results)
        {
            //reset audio manager
            mAudioManager = null;
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



            //start of stuff to do with input
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

//assign a sound to the sound pool
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            //loading up the siund ofr future use
            sound = sp.load(recorder.this, R.raw.sound1, 1);
//create a string to store the said text
            String result;
//strings used for comparison later on
            String where="where";
            String phone = "phone";
            String are = "are";
            String you = "you";



//the string then gets what was said
            result = (String) data.get(0);

//here is what we use to trigger the beeping and vibrating
            if ((result.toLowerCase().contains(where.toLowerCase()) == true && result.toLowerCase().contains(phone.toLowerCase()) == true) || (
                    result.toLowerCase().contains(are.toLowerCase()) == true && result.toLowerCase().contains(you.toLowerCase()) == true && result.toLowerCase().contains(where.toLowerCase()) == true)) {




                    mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                //this is for getting the max volume so the beep will be played at th volume
                    int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                //setting the max volume on the phone
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

//if the sound is loaded and ready to go then we execute the beeping noise below
                 if (sound != 0) {
                     v.vibrate(3000);
                    sp.play(sound, 1, 1, 0, 0, 1);
                    try{ Thread.sleep(1300); }catch(InterruptedException e){ }
                    sp.play(sound, 1, 1, 0, 0, 1);
                     try{ Thread.sleep(1300); }catch(InterruptedException e){ }



                }


            }


//then listen again
            mIsListening = false;
            Message message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
            try
            {
                mServerMessenger.send(message);
            }
            catch (RemoteException e)
            {

            }










        }
//not important to us
        @Override
        public void onRmsChanged(float rmsdB)
        {

        }
    }
//used for service:
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
