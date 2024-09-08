package com.example.ly;


import java.util.Optional;
import java.util.function.Function;

/**
 * @description: optional测试
 * @author: ly
 * @create: 2024-07-19 20:58
 **/
public class OptionalTest {

    public void test() {
        String str = "hello";
        Optional<String> optional = Optional.of(str);
        FunInterface funInterface = System.out::print;
        Function<User, String> getName = User::getName;
        optional.ifPresent(System.out::print);
        User user = new User();
//        optional.ifPresent(user::printStr);


        User user1 = new User();
//        Function<Object, ?> colum = User::getName;
//        user1.eq(User::getName, "test");


    }

    public static void main(String[] args) {
        OptionalTest optionalTest = new OptionalTest();
        optionalTest.test();

    }
}
