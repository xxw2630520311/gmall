package com.atguigu.gmall.testmapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.gmall.testmapper.mapper")
public class TestMapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMapperApplication.class, args);
    }

}
