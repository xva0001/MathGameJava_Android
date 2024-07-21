package com.example.itp4501_gameingx;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * BackgroundSoundService
 * Background Sound Service
 * it is music player in background

 * */
public class BackgroundSoundService extends Service {

    //
    private MediaPlayer player;

    /**
     * it volume value is read-only, set value is useless for changing volume
     * */
    public  static int volume;

    /**
     * it is for the testing binding, but static value is the best solution for set seekbar in setting activity
     * */
    public class  BackgroundSoundServiceBinder extends Binder{
        public BackgroundSoundService getBackgroundSoundService(){
            return BackgroundSoundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //set the song ( which is ncs(NoCopyrightSounds), please see the R.raw (.txt))
        player = MediaPlayer.create(this,R.raw.dreamer);
        player.setLooping(true);
        volume = 50;
        float vol = 0.5F;
        player.setVolume(vol,vol);
        //player.start();

    }

    /**
     * action setting
     * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);


        if (intent == null){
            return  START_STICKY;
        }
        if(Objects.equals(intent.getAction(), "sound.init")){
            player.start();
        };

        if (Objects.equals(intent.getAction(), "sound.adjust")) {

            String volStr = intent.getStringExtra("volumn");

            Log.d("test : ",volStr);

            int vol = Integer.parseInt(volStr);
            volume = vol;
            float volF = vol;
            volF = volF /100;
                        //err text:volumn --> volume
            Log.d("volumn",""+volF);//log print
            //0 means stop play sound
            if (vol<=0||vol>100){
                player.pause();
            } else {
                /**
                 * value between 0.0-1.0
                 * */
                player.setVolume(volF,volF);
                player.start();
            }

        }
        //player.start();
        return  START_STICKY;
    }




    @Override
    public void onDestroy() {
            player.pause();
        //player.release();
        super.onDestroy();

    }

    @Deprecated
    public void adjustMusicVolumn(float vol){
        player.pause();
        player.setVolume(vol,vol);
        player.start();
    }
    @Deprecated
    public int getVolumn(){
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        return am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }
}
