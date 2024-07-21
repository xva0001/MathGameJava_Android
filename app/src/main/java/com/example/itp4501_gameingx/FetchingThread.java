package com.example.itp4501_gameingx;

import android.util.Log;

import com.example.itp4501_gameingx.data_model.Ranking;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class FetchingThread extends Thread {

    public String url;



    String data = "";

    boolean isRuning =true;

    public FetchingThread(String url1){
        this.url = url1;

    }

    public void FetchString()
    {
        Thread thread = new Thread(new Runnable()
        {
            //@Override
            public  void  start(){
                run();
            }

            @Override
            public void run()
            {
                isRuning = true;
                try
                {
                    isRuning = true;
                    //Establish Connection:
                    URL urllink = new URL(url);
                    //Creates a URL object from the provided url string.
                    HttpURLConnection hpc = (HttpURLConnection) urllink.openConnection();
                    //
                    hpc.setReadTimeout(5000);
                    //Sets a read timeout of 5 seconds for the connection.
                    hpc.setRequestMethod("GET");
                    //Specifies that the connection will use the HTTP GET method to request data.
                    hpc.connect();
                    //connect to server

                    InputStream ips = hpc.getInputStream();
                    //get input stream
                    InputStreamReader ipr = new InputStreamReader(ips);
                    //read input stream

                    int inputStrData = ipr.read();
                    //read
                    while (inputStrData >=0){

                        char curr = (char)inputStrData;
                        inputStrData= ipr.read();
                        data += curr;
                    }

                    Log.d("data",""+data);
                    readAndParse(data);
                    //read and parse data
                    ips.close();
                    //close input stream
                    isRuning = false;
                }
                catch (Exception e)
                {

                    Log.d("error",e.toString());
                    isRuning = false;
                    return;
                }
                //isRuning = false;
            }});
        thread.start();
    }
    //thread.run();

    public  void readAndParse(String data){

        try{
            list = resultToList(data);

            isRuning = false;

        }catch (Exception ex){
            Log.d("error",ex.toString());
            return;
        }
    }
    ArrayList<Ranking>  list;

    public ArrayList<Ranking> getList() {
        //Stop = true;
        return list;

    }
    private ArrayList<Ranking> resultToList(String str){

        ArrayList<Ranking> res = new ArrayList<Ranking>();
        //Creates an empty ArrayList to store the deserialized Ranking objects.
        Ranking[] tempRankingArr =  new Gson().fromJson(str, Ranking[].class);
        //Uses the Gson library to deserialize the JSON string str into an array of Ranking objects (tempRankingArr). This assumes that the JSON structure matches the structure of the Ranking class

        Collections.addAll(res, tempRankingArr);
        //Adds all elements from the tempRankingArr array to the res ArrayList. This effectively populates the res list with the deserialized Ranking objects.



        return res;

    }
}
