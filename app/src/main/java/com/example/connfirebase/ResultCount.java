package com.example.connfirebase;

import java.util.ArrayList;

public class ResultCount {
    public Float[] getarray(String result, Float cnt_array[]) {

        if (result.equals("A")) {
            cnt_array[0] = cnt_array[0] + 1;
        } else if (result.equals("B")) {
            cnt_array[1] = cnt_array[1] + 1;
        }else if(result.equals("C")) {
            cnt_array[2] = cnt_array[2] + 1;
        }else if(result.equals("D")) {
            cnt_array[3] = cnt_array[3] + 1;
        }else if(result.equals("F")) {
            cnt_array[4] = cnt_array[4] + 1;
        }

        return cnt_array;
    }

    public Float[] getarray_1(String result, Float cnt_array_1[]) {

        if (result.equals("F")) {
            cnt_array_1[2] = cnt_array_1[2] + 1;
        }else if(result.equals("D")){
            cnt_array_1[1] = cnt_array_1[1] + 1;
        }else{
            cnt_array_1[0] = cnt_array_1[0] + 1;
        }

        return cnt_array_1;
    }
}
