package com.example.itp4501_gameingx.data_model;

import androidx.annotation.NonNull;

public class Ranking implements  Comparable<Object> {

    public  String  Name;
    public  int  Correct;
    public  int Time;


    @NonNull
    @Override
    public String toString() {
        return Name;
    }

    @Override
    public int compareTo(Object o) {

        return  this.compareTo(o.toString());

    }
}
