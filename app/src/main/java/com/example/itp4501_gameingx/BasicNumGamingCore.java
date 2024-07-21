package com.example.itp4501_gameingx;

import android.util.Log;

import java.util.Random;

/**
 * basic game core
 * */

public class BasicNumGamingCore {
    /**
     * for testing in vsc
     * */
    @Deprecated
    public static void main(String[] args) {
        BasicNumGamingCore x = new BasicNumGamingCore();
        System.out.println(x);
    }



    public int number1;
    public int number2;
    public String op;
    public int ans;
    public long ansEx2;

    public BasicNumGamingCore(){
        //infinitive for loop to choose number
        for(;;){
            number1 = NumberGeneration();
            number2 = NumberGeneration();
            op = OperationGeneration();
            Integer ansTemp = getAns(op, number1, number2);

            if (ansTemp!=-1) {
                ans =ansTemp;
                Log.d("TAG", "BasicNumGamingCore: " + ansTemp);
                break;
            }

        }
    }


    public BasicNumGamingCore(int i){
        //infinitive for loop to choose number
       if (i==1){
            for(;;){
                number1 = NumberGenerationEx2();
                number2 = NumberGenerationEx2();
                op = OperationGeneration();
                long ansTemp = getAnsEx2(op, number1, number2);
                if (ansTemp!=-1) {
                    Log.d("TAG", "BasicNumGamingCore: " + ansTemp);
                    ansEx2 =ansTemp;
                    break;
                }
            }
       }
       if (i==2){
           for(;;){
               number1 = NumberGenerationEx2();
               number2 = NumberGenerationEx2();
               op = OperationGeneration();
               if (op =="+" ||op=="-"){
                   continue;
               }
               long ansTemp = getAnsEx2(op, number1, number2);
               if (ansTemp!=-1) {
                   Log.d("TAG", "BasicNumGamingCore: " + ansTemp);
                   ansEx2 =ansTemp;
                   break;
               }
           }
       }
    }


    /***
     * get Random number 
     * 
     * @return a Integer;
     */
    private Integer NumberGeneration() {

        Random Snum1 = new Random();

        Integer num1 = Snum1.nextInt(100 - 1) + 1;
        // num1 = num1 %100;

        return num1;
    }

    /***
     * get Random number
     *
     * @return a Integer;
     */
    private Integer NumberGenerationEx2() {

        Random Snum1 = new Random();

        Integer num1 = Snum1.nextInt(9999) + 1;
        // num1 = num1 %100;

        return num1;
    }


    /**
     * random operation sign
     * @return operation sign
     */
    private String OperationGeneration() {
        Random rand = new Random();
        //big number to balance %
        Integer num1 = rand.nextInt(16384);
        // it is fair % for every operation (tested in 100 000 generation)
        return num1 < 750 ? "+" : num1 < 1500 ? "-" : num1 < 9000 ? "×" : "\u00F7"/** / sign */;

    }

    /**
     * 
     * @param op   is operation sign
     * @param num1 is number (First)
     * @param num2 is number (Second)
     * @return (@class)Integer if -1 (please redraw)
     */
    private Integer getAns(String op, Integer num1, Integer num2) {

        if (op == "+") {
            //only positive number
            if ((num1 + num2) > 100) {
                return -1;
            }
            return num1 + num2;
        }
        if (op == "-") {
            if ((num1 - num2) <= 0) {
                return -1;
            }
            return num1 - num2;
        }
        if (op == "×") {
            if ((num1 * num2) > 100) {
                return -1;
            }
            return num1 * num2;
        }
        if (op == "\u00F7") {
            if (num2==0){
                return -1;
            }
            if ((num1 % num2) != 0 || num1 < num2 || num1 == 0) {
                return -1;
            }
            return num1 / num2;
        }
        return -1;
    }


    private long getAnsEx2(String op, Integer num1, Integer num2) {

        if (op == "+") {
            //only positive number
            return (long)num1 + (long)num2;
        }
        if (op == "-") {
            if ((num1 - num2) <= 0) {
                return -1;
            }
            return (long)num1 - (long)num2;
        }
        if (op == "×") {
            return (long)num1 * (long)num2;
        }
        if (op == "\u00F7") {
            if (num2==0){
                return -1;
            }
            if ((num1 % num2) != 0 || num1 < num2 || num1 == 0) {
                return -1;
            }
            return (long)num1 / (long)num2;
        }
        return -1;
    }

    /**
     * toString
     * */
    public String toQuestionString(){return number1 +" " + op +" " + number2+ " = ";}
    public String toString(){
        return number1 +" " + op +" " + number2+ " = " + ans;
    }
}
