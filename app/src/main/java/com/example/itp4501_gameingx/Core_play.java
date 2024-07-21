package com.example.itp4501_gameingx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.itp4501_gameingx.dao.PlayRecordDAO;
import com.example.itp4501_gameingx.data_model.PlayRecord;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Core_play is a big file
 * let see inline of this program
 * */
public class Core_play extends AppCompatActivity {

    //main game logic (using @class BasicNumGamingCore)
    CoreGame mainGame ;

    //for inserting data and passing item to next Activity
    PlayRecord tempForNextActivityUsage ;
    /**
     * if null return new PlayRecord (1)
     * else return tempForNextActivityUsage
     * PlayRecord formatted
     * (include formatting and data strut)
     * @return PlayRecord
     * */
    private PlayRecord getRecord(){
        /**
         * (1)
         * */
        if (tempForNextActivityUsage!=null){
            return tempForNextActivityUsage;
        }
        /** get curr time
         * set to calendar
         * */
        Calendar calendar = Calendar.getInstance();
        long dateTime = System.currentTimeMillis();
        calendar.setTimeInMillis(dateTime);
        //formatting
        int mYear = calendar.get(Calendar.YEAR);
        //String formatted = String.format("%03d", num);

        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int second = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE); // Gets the current minute
        int[] Datearr = {mMonth,mDay,second,minutes,mHour};
        ArrayList<String> res=new ArrayList<String>();

        for (int i: Datearr) {
            String formatted = String.format("%02d", i);
            res.add(formatted);
        }
        //end formatting
        //set value
        String dateStr = res.get(1) + "-"+res.get(0) +"-"+ mYear;

        String timeStr =  res.get(4)+":"+ res.get(3) + ":" + res.get(2) ;
        //new PlayRecord
        tempForNextActivityUsage = new PlayRecord(dateStr, timeStr, mainGame.trueCounter, mainGame.playTime);
        return  tempForNextActivityUsage;

    }
    //disable number enter
    private void disableNumberInput(){
        for (int i: keypad) {
            Button btn = findViewById(i);
            btn.setEnabled(false);
        }
        for (int i:fun_keypad) {
            Button btn = findViewById(i);
            btn.setEnabled(false);
        }
    }
    //enable
    private void enableNumberInput(){
        for (int i: keypad) {
            Button btn = findViewById(i);
            btn.setEnabled(true);
        }
        for (int i:fun_keypad) {
            Button btn = findViewById(i);
            btn.setEnabled(true);
        }
    }
    //set onClick in xml (!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!)
    public void  onNumberClick(View view){
        //get btn, editText
        Button btnNum;
        EditText txtAns = findViewById(R.id.txtEnterNumber);
        try {
            btnNum = (Button) view;
            String tempString = txtAns.getText().toString();
            // it can input 4 digital
            if (tempString.length() > 3 ){
                return;
            }
            tempString += btnNum.getText().toString();
            txtAns.setText(tempString);
        }catch (Exception e){
            Log.i("btn ", "onNumberClick: " + e.toString());
            return;
        }

    }
    protected void setupView(){
        //button confirm
        Button btnConfirm = findViewById(R.id.btnNunAnsConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtAns = findViewById(R.id.txtEnterNumber);
                try {
                    Integer inputAns = Integer.parseInt(txtAns.getText().toString());
                }catch (Exception e){
                    return;
                }
                try{

                    mainGame.checkAns();
                }catch (Exception e){

                    return;
                }
                if (mainGame.questionCounter ==10){
                    getRecord();
                    Intent i = new Intent(Core_play.this,FinishActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //do something
                    mainGame.onStop();
                    TextView tex = findViewById(R.id.txtQuestionTitle);
                    tex.setText("End");
                    TextView textView = findViewById(R.id.txtQuestion);
                    textView.setText("");
                    disableNumberInput();
                    try {
                        accessDB insertDB = new accessDB();
                        insertDB.start();
                        while (insertDB.isRunning) {

                        }
                        if(getRecord() != null){
                            i.putExtra("record",new Gson().toJson(getRecord()));
                        }
                    }catch (Exception e){
                        startActivity(i);
                        return;
                    }

                    startActivity(i);
                    return;
                }
                //Timer nextQues


                mainGame.newQuestion();
            }
        });

        //delete
        Button btnDel = findViewById(R.id.btnNumDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtAns = findViewById(R.id.txtEnterNumber);
                if (txtAns.getText().toString().length() == 0){
                    return;
                }

                if (txtAns.getText().toString().length() == 1){
                    txtAns.setText("");
                    return;
                }

                String temp = txtAns.getText().toString();
                temp = temp.substring(0,temp.length()-1);
                txtAns.setText(temp);

            }
        });

        //end delete

        //clear
        Button btnClear = findViewById(R.id.btnClearAll);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtAns = findViewById(R.id.txtEnterNumber);
                txtAns.setText("");
            }
        });
        //end clear




        //image button back
        ImageButton btnBack = findViewById(R.id.imgbtnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {

                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Core_play.this);
                        alertDialogBuilder.setTitle(R.string.core_play_alter);
                        alertDialogBuilder.setPositiveButton
                            (
                                    R.string.confirm, new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent i = new Intent(Core_play.this, MainActivity.class);
                                            startActivity(i);

                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                            finish();
                                        }
                                    }
                            );
                        alertDialogBuilder.setNegativeButton(R.string.back,null);
                        alertDialogBuilder.show();
                    }
               }
        );
        //end image btn

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_core_play_view);
        mainGame = new CoreGame();
        Intent intent = getIntent();
        Intent musicBGMintent = new Intent(Core_play.this,BackgroundSoundService.class);

        startService(musicBGMintent);

        String vol = intent.getStringExtra("bgmvol");

        Log.i("test : " , "onCreate: " + vol);
        musicBGMintent.setAction("sound.adjust");
        //volumn

        musicBGMintent.putExtra("volumn",vol);
        startService(musicBGMintent);
        Log.i(" opopop", "onCreate: " + BackgroundSoundService.volume  );

        setupView();

        disableNumberInput();
        //countDownTimer.start();
        //startTimer.schedule();
        mainGame.onStart();
        //start game
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (isLeaved){
            //is Leaved

            Intent intent = new Intent(Core_play.this, MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra("msg : "," user leaved the game");

            startActivity(intent);

            finish();
        }


    }




    private final int[] keypad =
            {R.id.btnNum0,
            R.id.btnNum1,R.id.btnNum2,R.id.btnNum3,
            R.id.btnNum4,R.id.btnNum5,R.id.btnNum6,
            R.id.btnNum7,R.id.btnNum8,R.id.btnNum9
            };
    private  final  int[] fun_keypad = {R.id.btnClearAll,R.id.btnNumDelete,R.id.btnNunAnsConfirm};

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //recover method
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        Log.i("test",event.getSource()+"");

        Log.i("TAG" + "keyboard input : ", "onKeyDown: " + keyCode);
        if (keyCode >= 7 && keyCode <= 16)
        {
            int keymapping = keyCode - 7;
            Button btnNumKeyboard = findViewById(keypad[keymapping]);
            btnNumKeyboard.callOnClick();
        }
        if (keyCode >= 114 && keyCode <= 153){
            int keymapping = keyCode - 114;
            Button btnNumKeyboard = findViewById(keypad[keymapping]);
            btnNumKeyboard.callOnClick();
            return  true;
        }
//useless : it cannot catch input from keyboard
        if(    keyCode == KeyEvent.KEYCODE_ENTER || keyCode == 160){
            Button btn = findViewById(fun_keypad[2]);
            btn.callOnClick();
            return true;
        }

        if (keyCode == 67  ){
            Button btn = findViewById(fun_keypad[1]);
            btn.callOnClick();
            return true;
        }

        if (keyCode == 112) {
            Button btn = findViewById(fun_keypad[0]);
            btn.callOnClick();
            return true;
        }


        return false;
    }

    boolean isLeaved= false;

    @Override
    protected void onPause() {

        //Intent musicBGMintent = new Intent(Core_play.this,BackgroundSoundService.class);

        //musicBGMintent.setAction("sound.restartForCorePlay");
        //stopService(musicBGMintent);
        //keyboard = null;
        mainGame.onStop();

        mainGame = null;
        tempForNextActivityUsage = null;

        isLeaved = true;

        super.onPause();
    }




    //game logic
    private class CoreGame{
        //attr
        int questionCounter  =  0;
        int wrongCounter = 0;
        int trueCounter = 0;
        public long playTime=0;
        Resources res = getResources();
        final String[] charDialogTextTrue = {res.getText(R.string.scriptTrue).toString(),res.getText(R.string.scriptTrue2).toString(),res.getText(R.string.scriptTrue3).toString(),res.getText(R.string.scriptTrue4).toString()};
        final String [] charDialogTextFalse ={res.getText(R.string.scriptWrong).toString(),res.getText(R.string.scriptWrong2).toString(),res.getText(R.string.scriptWrong3).toString(),res.getText(R.string.scriptWrong4).toString(),res.getText(R.string.scriptWrong5).toString(),};

        CountDownTimer gameStartCountDownInfinitive = new CountDownTimer(Long.MAX_VALUE,10) {

            @Override
            public void onTick(long millisUntilFinished) {

                TextView txtTime = findViewById(R.id.txtTimer);
                long time = Long.MAX_VALUE - millisUntilFinished;
                playTime = time;
                NumberFormat formatter = new DecimalFormat("#0.00");
                Double eTime = (double) time / 1000;

                String strTime = getResources().getText(R.string.time) + " " ;//+ formatter.format(eTime) + "s";

                if (eTime / 60.00 > 1 ){

                    strTime += (eTime.intValue() / 60) + " m" ;
                    String tempSecond = formatter.format(eTime % 60.00) + " s";
                    strTime += " "+tempSecond;
                }else {
                    strTime = getResources().getText(R.string.time) + " " + formatter.format(eTime) + "s";
                }
                txtTime.setText(strTime);


            }

            @Override
            public void onFinish() {

            }
        };//end game timer define
        CountDownTimer countDownTimer = new CountDownTimer(3000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView txtTime = findViewById(R.id.txtTimer);
                Long msuf = millisUntilFinished;
                Double strmsuf = msuf.doubleValue()  /  1000;
                NumberFormat formatter = new DecimalFormat("#0.00");
                txtTime.setText(  formatter.format(strmsuf) + " s");
            }

            @Override
            public void onFinish() {
                TextView txtTime = findViewById(R.id.txtTimer);
                txtTime.setText(R.string.start);

                enableNumberInput();
                newQuestion();
                gameStartCountDownInfinitive.start();
            }
        };
        ///end start game timer

        BasicNumGamingCore numGamingCore;
        protected void onStart(){
            countDownTimer.start();
        }
        protected void onStop(){

            countDownTimer.cancel();
            gameStartCountDownInfinitive.cancel();
        }
        protected void newQuestion(){
            numGamingCore = new BasicNumGamingCore();
            TextView txtQuestionTitle = findViewById(R.id.txtQuestionTitle);
            TextView txtQuestion = findViewById(R.id.txtQuestion);
            EditText txtAns = findViewById(R.id.txtEnterNumber);
            txtAns.setText("");
            txtQuestion.setText(numGamingCore.toQuestionString());
            questionCounter++;
            Timer responTimer = new Timer(true);
            TimerTask showText = new TimerTask() {
                @Override
                public void run() {
                    try {
                        txtQuestionTitle.setText(res.getText(R.string.question) + " " + questionCounter);
                    }catch (Exception e){
                        return;
                    }
                }
            };
            long delay = 3000;
            long pre = 1500;
            responTimer.schedule(showText,delay,pre);

        }

        protected void checkAns(){
             Integer ans = numGamingCore.ans;
             TextView txtAns = findViewById(R.id.txtEnterNumber);
            try
            {
                Integer inputAns = Integer.parseInt(txtAns.getText().toString());
            }catch (Exception e ){
                throw  e;
                //..  return;
            }
            Integer inputAns = Integer.parseInt(txtAns.getText().toString());

             TextView txtQuestion = findViewById(R.id.txtQuestion);
             TextView txtCharDialog= findViewById(R.id.txtCharDialog);
            TextView txtQuestionTitle = findViewById(R.id.txtQuestionTitle);

             if (inputAns == ans){
                 txtQuestionTitle.setText(getResources().getText(R.string.correct));
                trueCounter++;
                txtCharDialog.setText(getCharacterDialogStringByMark(true));
             }else {
                 txtQuestionTitle.setText(res.getText(R.string.wrong));
                 wrongCounter++;
                 txtCharDialog.setText(getCharacterDialogStringByMark(false));

             }
            //(new Handler()).postDelayed(this::null, 5000);
        }

        private String getCharacterDialogStringByMark(boolean nf){
            if (nf){
                if (trueCounter >= 8){
                    // arr num 3
                    return charDialogTextTrue[charDialogTextTrue.length-1];
                }
                if (trueCounter >= 6){
                    //2
                    return  charDialogTextTrue[charDialogTextTrue.length-2];

                }
                if (trueCounter >= 4){
                    //arr num 1
                    return charDialogTextTrue[charDialogTextTrue.length -3];
                }
                return  charDialogTextTrue[0];


            }else {
                if (wrongCounter <= 2){
                    return charDialogTextFalse[0];
                }
                if (wrongCounter <=4){
                    return charDialogTextFalse[1];
                }
                if (wrongCounter <= 6){
                    return charDialogTextFalse[2];
                }
                if (wrongCounter <= 8){
                    return  charDialogTextFalse[3];
                }
                return  charDialogTextFalse[4];
            }
        }
    }//Core Game

    //insert data to db using room
    private class  accessDB extends  Thread{

        public  void  start(){
            run();
        }
        public  boolean isRunning = false;

        @Override
        public void run() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRunning = true;

                    try {
                        PlayRecordDatabase db =
                                Room.databaseBuilder(getApplicationContext(),
                                        PlayRecordDatabase.class,
                                        "player_record_db").build();

                        PlayRecordDAO pr = db.playRecordDAO();

                        PlayRecord newPlayerRecordInstance = getRecord();
                        tempForNextActivityUsage = newPlayerRecordInstance;
                        pr.insertAll(newPlayerRecordInstance);
                    }catch (Exception e){

                        isRunning = false;
                        return;
                    }
                    isRunning = false;
                }
            });
            thread.start();
        }


    }


}//Core_play

