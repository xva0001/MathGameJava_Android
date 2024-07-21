package com.example.itp4501_gameingx;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.itp4501_gameingx.dao.PlayRecordDAO;
import com.example.itp4501_gameingx.data_model.PlayRecord;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record);

    }
    ArrayList<PlayRecord> dataArr2;
    @Override
    protected void onResume() {
        super.onResume();
        accessDBThread selectData = new accessDBThread();

        selectData.getDBData();

        while (selectData.isRuning){
            dataArr2 = selectData.getList();
        }
        GridView gridRecord = findViewById(R.id.gridRecord);

        if ( selectData.dataArr == null && dataArr2 == null){

            return;
        }
        gridRecord.setAdapter(new RecordAdapter(RecordActivity.this,selectData.dataArr));

    }

    private  class accessDBThread extends  Thread{

        ArrayList<PlayRecord> dataArr;

        boolean isRuning = true;

        public void getDBData(){
            Thread thread = new Thread(new Runnable() {

                public  void  start(){
                    run();
                }
                @Override
                public void run() {
                    isRuning = true;
                    PlayRecordDatabase db = Room.databaseBuilder(RecordActivity.this
                            ,PlayRecordDatabase.class
                            ,"player_record_db").build();
                    PlayRecordDAO playRecordConn =db.playRecordDAO();
                    List<PlayRecord> list = playRecordConn.getAll();
                    for (PlayRecord t: list
                         ) {
                        Log.d("TAG", "run: " + t.getDuration());
                    }
                    dataArr = new  ArrayList<PlayRecord>(list);
                    dataArr2 = dataArr;
                    isRuning = false;
                }
            });
            thread.start();

        }
        public ArrayList<PlayRecord> getList(){
            return  dataArr;
        };

    }
}