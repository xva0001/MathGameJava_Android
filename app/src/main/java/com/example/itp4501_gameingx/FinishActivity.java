package com.example.itp4501_gameingx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itp4501_gameingx.data_model.PlayRecord;
import com.google.gson.Gson;

public class FinishActivity extends AppCompatActivity {

    public void  setupMethod(){

        Button btnContinue = findViewById(R.id.btnfinContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this, Core_play.class);
                intent.putExtra("bgmvol",""+BackgroundSoundService.volume);
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });
        //end btnContinue

        Button btnBack  =  findViewById( R.id.btnfinBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this, MainActivity.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
            }
        });

    }

    private void getDataForIntent(){
        try{
            Intent dataIntent = getIntent();
            //Obtains the Intent object that was used to start the current activity
            String objPlayRecord = dataIntent.getStringExtra("record");
            //Extracts a string value associated with the key "record" from the intent.
            PlayRecord record = new Gson().fromJson(objPlayRecord, PlayRecord.class);

            // This string presumably contains a JSON representation of a PlayRecord object
            setData(record);
        }catch (Exception e){
            setData(null);
            return;
        }

    }

    private   void  setData(PlayRecord re){
        //Null Check:
        if (re==null){
            TextView tvCorr = findViewById(R.id.txtfinCorrect);
            TextView tvWrong = findViewById(R.id.txtfinWrong);
            TextView tvTime = findViewById(R.id.txtfinTime);
            TextView[] arr = {tvTime,tvCorr,tvWrong};
            for (TextView i :arr){
                i.setText("Error");
            }

            return;
        }

        TextView tvCorr = findViewById(R.id.txtfinCorrect);

        String tempTiitleString = tvCorr.getText().toString();

        tvCorr.setText(tempTiitleString+ " : " + re.correctCount);

        TextView tvWrong = findViewById(R.id.txtfinWrong);

        tempTiitleString = tvWrong.getText().toString();

        int wrongNum = 10 - re.correctCount;

        tvWrong.setText(tempTiitleString +" : " + wrongNum);

        TextView tvTime = findViewById(R.id.txtfinTime);

        tempTiitleString = tvTime.getText().toString();

        tvTime.setText(tempTiitleString+" : " + re.getDuration());

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish);

        setupMethod();
        //optional
//        Intent intent = getIntent();
//        Intent musicBGMintent = new Intent(FinishActivity.this,BackgroundSoundService.class);
//
//        startService(musicBGMintent);
//
//        String vol = intent.getStringExtra("bgmvol");
//
//        Log.i("test : " , "onCreate: " + vol);
//        musicBGMintent.setAction("sound.adjust");
//        //volumn
//
//        musicBGMintent.putExtra("volumn",vol);
//        startService(musicBGMintent);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        getDataForIntent();

    }

    @Override
    protected void onResume() {

        super.onResume();
    }


}