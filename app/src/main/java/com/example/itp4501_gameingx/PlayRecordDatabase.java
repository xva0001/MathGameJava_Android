package com.example.itp4501_gameingx;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.itp4501_gameingx.dao.PlayRecordDAO;
import com.example.itp4501_gameingx.data_model.PlayRecord;

/**
 * It's an abstract method that returns an instance of PlayRecordDAO.
 * This DAO (Data Access Object) will define
 * the methods for interacting with the PlayRecord entities in the database
 *
 * Disables exporting the schema to a JSON file.
 * This is often set to false for production apps
 * */
@Database(entities = {PlayRecord.class},version =  1 , exportSchema = false)
public abstract class PlayRecordDatabase extends RoomDatabase {

    public  abstract PlayRecordDAO playRecordDAO();
}
