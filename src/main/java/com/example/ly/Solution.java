package com.example.ly;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @description: Solution
 * @author: ly
 * @create: 2024-07-30 15:56
 **/
public class Solution {

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int first = nums[0], second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {1, 10, 2, 4};
        System.out.println(solution.rob(nums));

        LinkedBlockingDeque<String> strings = new LinkedBlockingDeque<>(100);
    }
}
