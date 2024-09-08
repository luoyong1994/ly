package com.example.ly;

public class Test extends Object {

    private Test() {

        try{
            Class<?> r = Class.forName("");
            Class<?>[] interfaces = r.getInterfaces();

        }catch (Exception e){

        }
    }

    private User user;

    public static class Builder {
        private final Test test = new Test();

        public Builder(User user) {
            test.user = user;
        }

        public Test build() {
            test.user.setName("luoyong");
            return test;




        }

    }



}
