package com.example.itp4501_gameingx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.room.Room;

import com.example.itp4501_gameingx.dao.PlayRecordDAO;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingActivity extends AppCompatActivity {


    protected void setUpMethod(){

        Spinner langSpinner = findViewById(R.id.spiLang);
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((String) parent.getItemAtPosition(position).toString()).equals("中文")){

                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.wrap(LocaleList.forLanguageTags("zh-rTW")));


                }else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.wrap(LocaleList.forLanguageTags("en")));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String langCurr = AppCompatDelegate.getApplicationLocales().toLanguageTags();
                Log.d("lang : ", langCurr);
                if (langCurr.equals("en")){
                     parent.setSelection(0);
                }else {
                    parent.setSelection(1);
                }
            }
        });

        SeekBar skbVol = findViewById(R.id.skbVolumn);
        skbVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("test : ", "onProgressChanged: "+ progress);

                Intent musicModify = new Intent(SettingActivity.this, BackgroundSoundService.class);

                musicModify.setAction("sound.adjust");
                musicModify.putExtra("volumn",""+progress);
                startService(musicModify);



            }//setOnSeekBarChangeListener

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Log.d("test : ",seekBar.getProgress()+"");
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Log.d("test : ",seekBar.getProgress()+"");
                return;
            }
        });

        Button btnClear = findViewById(R.id.btnClearRecord);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder alertDialog = new  MaterialAlertDialogBuilder(SettingActivity.this);

                alertDialog.setTitle(R.string.clear_record);
                alertDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accessDB deleteDB = new accessDB();
                        deleteDB.deleteAllRecord();
                        while (deleteDB.isRunning){
                            btnClear.setEnabled(false);
                        }
                        btnClear.setEnabled(true);
                    }
                });

                alertDialog.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }
                );
                alertDialog.show();
            }
        });



    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activty_setting);


        Spinner langSpinner = findViewById(R.id.spiLang);


        ArrayAdapter<CharSequence> arrLang = ArrayAdapter.createFromResource(this,R.array.lang_list,R.layout.bg_spinner);

        arrLang.setDropDownViewResource(R.layout.bg_spinner);

        langSpinner.setAdapter(arrLang);
        setUpMethod();



    }

    protected  void setupResume(){

        //langSpinner
        Spinner langSpinner = findViewById(R.id.spiLang);
        String langCurr = AppCompatDelegate.getApplicationLocales().toLanguageTags();
        Log.d("lang : ", langCurr);
        if (langCurr.equals("en")){
            langSpinner.setSelection(0);
        }else {
            langSpinner.setSelection(1);
        }
        //end langSpinner




        //seekbar
        SeekBar skbVol = findViewById(R.id.skbVolumn);
        //static value
        skbVol.setProgress(BackgroundSoundService.volume);

        //end seekbar

    };

    @Override
    protected void onResume() {
        super.onResume();
        //setup Resume
        setupResume();

    }

    @Override
    protected void onStop() {


        super.onStop();
    }

private  class  accessDB extends Thread{

       public boolean isRunning = true;

        public void  deleteAllRecord(){
            Thread thread = new Thread(new Runnable() {
                public void start(){
                    Log.d("TAG test", "start: " + "  w");
                    run();
                }
                @Override
                public void run() {
                    Log.d("TAG test", "run: " + "  w");
                    // Creates a Room database instance
                    PlayRecordDatabase db = Room.databaseBuilder(
                            getApplicationContext(),
                            PlayRecordDatabase.class,
                            "player_record_db").build();
                    // Gets the DAO for the PlayRecord entity
                    PlayRecordDAO pr = db.playRecordDAO();

                    // Calls the deleteAll() method on the DAO,
                    pr.deleteAll();

                    //db.close();
                    isRunning = false;
                }
            });
            thread.start();
        }

    @Override
    public void run() {
        deleteAllRecord();;
    }

}


}
