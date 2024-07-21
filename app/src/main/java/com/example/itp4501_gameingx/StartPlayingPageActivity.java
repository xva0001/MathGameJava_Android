package com.example.itp4501_gameingx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StartPlayingPageActivity extends AppCompatActivity {

    public void setupMethod(){

        Button btnBack= findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayingPageActivity.this, Core_play.class);
                intent.putExtra("bgmvol",""+BackgroundSoundService.volume);
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

                startActivity(intent);

            }
        });
        //ex1 btn

        Button btnEx1 = findViewById(R.id.btnEx1);
        btnEx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayingPageActivity.this, Ex1_play.class);
                intent.putExtra("bgmvol",""+BackgroundSoundService.volume);
                startActivity(intent);
            }
        });

        //ex2 btn

        Button btnEx2 = findViewById(R.id.btnEx2);
        btnEx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayingPageActivity.this, Ex2_play.class);
                intent.putExtra("bgmvol",""+BackgroundSoundService.volume);
                startActivity(intent);
            }
        });

        //btn ex3
        Button btnEx3 = findViewById(R.id.btnEx3);
        btnEx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPlayingPageActivity.this, Ex3_play.class);
                //put bgm volume to intent for next activity
                intent.putExtra("bgmvol",""+BackgroundSoundService.volume);
                startActivity(intent);
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_play_game);
        setupMethod();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}