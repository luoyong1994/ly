package com.example.ly;

import com.example.ly.nettychat.NettyClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;

@SpringBootApplication
@MapperScan("com.example.ly.**")
public class LyApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(LyApplication.class, args);
        NettyClient.main(args);
    }

}
