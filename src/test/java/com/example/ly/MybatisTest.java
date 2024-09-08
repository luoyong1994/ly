package com.example.ly;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description: mybatis函数特性探索
 * @author: ly
 * @create: 2024-07-20 13:04
 **/
@SpringBootTest
public class MybatisTest {


    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void sqlTest() {
        LambdaQueryWrapper<Model> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(Model::getID_, "name");
        List<Model> models = modelMapper.selectList(userLambdaQueryWrapper);


        System.out.println("结果-------");
    }
}
