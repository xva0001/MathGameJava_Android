package com.example.itp4501_gameingx.data_model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "play_record")
public class PlayRecord
{
    @PrimaryKey (autoGenerate = true)
   public Integer gameID;

    public String playDate;

    public String playTime;

    public  int correctCount;

    public long duration;
    public  PlayRecord(){

    }

    public PlayRecord(String playDate,String playTime,Integer correct, long time){
        this.playDate=playDate;
        this.playTime =playTime;
        correctCount =correct;
        duration = time;
    }

    public  String getDateTime(){

        return playDate + " " + playTime;

    };

    public  String getDuration(){

        //String str =  String.format("%02d",);
        long minutes = (duration / 1000) / 60;

        // formula for conversion for
        // milliseconds to seconds
        long seconds = (duration / 1000) % 60;


        return  String.format("%02d",minutes) + ":" + String.format("%02d",seconds) ;


    }





}
