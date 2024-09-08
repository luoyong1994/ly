package com.example.ly;

/**
 * 动态规划测试
 */
public class DynicPlanTest {


    //优化前
    public static int fib(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    //优化后
    public static int fibDynic(int n) {
        int[] result = new int[n + 1];
        result[1] = 1;
        result[0] = 0;
        for (int i = 2; i <= n; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }
        return result[n];
    }

    public static void main(String[] args) {
        //0,1,1,2,3,5,8,13
        //0,1,2,3,4,5,6,7
        int n = 40;
        long l = System.currentTimeMillis();
        int fib = fib(n);
        System.out.println("fib:" + fib);
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l);
        int fibDynic = fibDynic(n);
        System.out.println("fibDynic:" + fibDynic);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
    }
}




























