package com.example.ly;

public class LamdaTest {


    public static void main(String[] args) throws InterruptedException {
        FunInterface funInterface;
        while (true) {
            Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            funInterface = (str) -> {
                Thread.sleep(100);
                System.out.println(str);
            };
            for (int i = 0; i < integers.length; i++) {
                funInterface.print(String.valueOf(integers[i]));
            }
            System.out.println(funInterface);
        }

    }


}
