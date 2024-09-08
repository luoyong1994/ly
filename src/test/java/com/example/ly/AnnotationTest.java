package com.example.ly;


import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnnotationTest {


    /**
     *
     * 重复性注解问题
     *
     */
    @RepeatedTest(value = 10)
    @DisplayName("repeat test ")
    void repeatTest() {
        System.out.println("test.....");
    }


    /**
     *
     * 参数化测试方法
     *
     * @param params
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void parameterTest(int params) {
        System.out.println(params);
    }



}
