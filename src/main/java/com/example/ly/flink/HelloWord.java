package com.example.ly.flink;

import java.util.ArrayList;
import java.util.List;

public class HelloWord {

    public static void main(String[] args) {
        //创建执行环境
        WindowApi_Demo windowApiDemo = new WindowApi_Demo();
        HelloWord helloWord = new HelloWord();
        List<WindowApi_Demo> test = helloWord.<WindowApi_Demo>getTest();

    }


    //方法泛型
    public <T> List<T> getTest() {
        return new ArrayList<>();
    }
}
