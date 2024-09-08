package com.example.ly;

public class TestApplicationLoader {


    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader appClassLoader = Test.class.getClassLoader();
        ClassLoader parent = appClassLoader.getParent();
        ClassLoader parent1 = parent.getParent();
        Package[] definedPackages = parent.getDefinedPackages();
        for (int i = 0; i < definedPackages.length; i++) {
            Package definedPackage = definedPackages[i];
            String name = definedPackage.getName();
            System.out.println(name);
        }

        Class<?> aClass = Test.class.getClassLoader().loadClass("com.fasterxml.jackson.core.Base64Variants");
        System.out.println(aClass);
    }
}
