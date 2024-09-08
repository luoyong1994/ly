package com.example.ly;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    String eq(Function colum, String value) {
        return null;
    }

    public int findTargetNumIndex(int[] num, int target) {
        int left = 0;
        int right = num.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (num[mid] == target) {
                return mid;
            } else if (num[mid] < target) {
                left = mid + 1;//右边查找
            } else {
                right = mid - 1;//左边查找
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5};
        User user = new User();
        int targetNumIndex = user.findTargetNumIndex(num, 1);
        System.out.println(targetNumIndex);
    }
}
