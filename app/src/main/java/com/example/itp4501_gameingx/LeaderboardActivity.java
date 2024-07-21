package com.example.itp4501_gameingx;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itp4501_gameingx.data_model.Ranking;
//import com.example.itp4501_gameingx;

import java.util.ArrayList;


public class LeaderboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        GridView gridView = findViewById(R.id.gridLeader);

    }

    ArrayList<Ranking> dataArr;
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        String url ="https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run";
        //countDownTimer.start();
        try {
            FetchingThread myThread = new FetchingThread(url);
            myThread.FetchString();
            TextView tlLeaderTitle = findViewById(R.id.txtLeaderboard);
            while (myThread.isRuning)
            {
                //dataArr = myThread.getList();
                tlLeaderTitle.setText("Waiting");
            }
            dataArr = myThread.getList();
            tlLeaderTitle.setText(R.string.leaderboard);

        }catch (Exception e){
            Log.d("TAG", "onPostCreate: " + e.toString());
            return;
        }
        GridView gridView = findViewById(R.id.gridLeader);
        for (Ranking i: dataArr
        ) {
            Log.d("TAG", "onResume: " + i.Name);
        }
        gridView.setAdapter(new LeaderboardAdapter(
                LeaderboardActivity.this,dataArr));

    }
}