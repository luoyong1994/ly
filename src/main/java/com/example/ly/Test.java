package com.example.ly;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("" + i);
            users.add(user);
        }

        users.stream().forEach(item -> {
            System.out.printf(item.toString());
        });

    }
}
