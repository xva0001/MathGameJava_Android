package com.example.itp4501_gameingx;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * main program entry point
 *
 * @method  MainActivity .setUpConstantMethod
 * setupMethod or  setUpConstantMethod is to set basic method to button, textview or
 * other
 * @see android.view
 *
 * */
public class MainActivity extends AppCompatActivity {
    protected void  setUpConstantMethod(){

        //Button : Setting
        Button btnSetting = (Button) findViewById(R.id.btnSetting);
        //set method
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change Activity
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);

                startActivity(intent);
                //animation
                overridePendingTransition(android.R.anim.fade_in,0);

            }
        });
        // first button : play
        Button btnPlay = findViewById(R.id.btnPlay);
        // set method
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change Activity
                Intent intent = new Intent(MainActivity.this, StartPlayingPageActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,0);

            }
        });

        //


        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LeaderboardActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    return;
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        Button btnRecord = findViewById(R.id.btnRecord);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this, RecordActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });



        Button btnleave = findViewById(R.id.btnMainLeave);
        btnleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //end program
                finishAffinity();
            }
        });
    }

    //Service musicService = new BackgroundSoundService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //see the top of this  java file
        setUpConstantMethod();

        //leave game msg
        if (getIntent().hasExtra("msg : ")){
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(MainActivity.this);
            dialog.setTitle(R.string.leave_game);
            //dialog.setMessage(getIntent().getStringExtra("msg : "));
            dialog.setPositiveButton(R.string.confirm,null);
            dialog.show();
        }

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //musicService
        Intent musicServiceIntent = new Intent(MainActivity.this, BackgroundSoundService.class);
        //init music
        musicServiceIntent.setAction("sound.init");
        startService(musicServiceIntent);
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Intent musicServiceIntent = new Intent(MainActivity.this, BackgroundSoundService.class);
        //stop music Service
        stopService(musicServiceIntent);
        super.onDestroy();
    }
}