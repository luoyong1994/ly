package com.example.ly.visitorpattern;

import java.util.StringTokenizer;

public class Client {

    public static void main(String[] args) {
        StringTokenizer stringTokenizer = new StringTokenizer("A b"," ");
        while (stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken());
        }
    }
}
