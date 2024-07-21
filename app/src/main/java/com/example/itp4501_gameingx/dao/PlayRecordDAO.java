package com.example.itp4501_gameingx.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.example.itp4501_gameingx.data_model.PlayRecord;

import java.util.List;

@Dao
public interface PlayRecordDAO {

    @Query("Select * from play_record")
    List<PlayRecord> getAll();

    @Insert
    void  insertAll(PlayRecord ...playRecords);

    @Query ("Delete from play_record")
    void deleteAll();

}
