package com.example.itp4501_gameingx;

import android.net.http.*;

import com.example.itp4501_gameingx.data_model.Ranking;
import com.google.gson.Gson;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.*;
import java.util.ArrayList;
import java.util.Collections;
//**
//
// */
@Deprecated
public class FetchingData {


    public static void main(String[] args) {
                // HttpClient client = HttpClient.newHttpClient();
                // HttpRequest request = HttpRequest.newBuilder()
                //         .uri(URI.create("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run"))
                //         .build();

                // CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

                // responseFuture.thenAccept(response -> {
                //     int statusCode = response.statusCode();
                //     String responseBody = response.body();
                //     System.out.println("Status Code: " + statusCode);
                //     System.out.println("Response Body:\n" + responseBody);
                // }).join();
        FetchingData test = new FetchingData("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run");
        try{
            test.sendRequest();
        }catch (Exception e){
            System.out.println(e.toString());
            return;
        }

        System.out.println(test.content);
        for (Ranking i: test.resultToList()) {
            System.out.println(i);
        }




    }
    
    HttpURLConnection client = null;
    HttpResponseCache request;
    public FetchingData(String uri){
        
        String re = "^(https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        boolean isURL = uri.matches(re);
        if (!isURL) {
            uri = null;
            return;
        }

        this.uri = URI.create(uri);

        //request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
    }

    private  URI uri;
    public String content = null;

    public void sendRequest() throws IOException {

//        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//
//        responseFuture.thenAcceptAsync(response -> {
//            //int statusCode = response.statusCode();
//            String responseBody = response.body();
//            //System.out.println("Status Code: " + statusCode);
//            //System.out.println("Response Body:\n" + responseBody);
//            content = responseBody.toString();
//        }).join();
        client = (HttpURLConnection) uri.toURL().openConnection();
        client.setRequestMethod("Get".toUpperCase());



        client.setReadTimeout(1000);

        InputStream tempStream = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(tempStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        content = response.toString();
    }

    public ArrayList<Ranking> resultToList(){

        ArrayList<Ranking> res = new ArrayList<Ranking>();
        Ranking[] tempRankingArr =  new Gson().fromJson(content, Ranking[].class);


        Collections.addAll(res, tempRankingArr);


        return res;

    }



}
