package com.gallo.it.components;

public class Counter{
    private static int count = 0;

    public static int getCount(){
        return count++;
    }

    public static void decrease(){
        count--;
    }

    public static void reset(){
        count = 0;
    }
}
