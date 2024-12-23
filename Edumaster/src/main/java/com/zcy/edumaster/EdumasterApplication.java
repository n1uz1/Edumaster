package com.zcy.edumaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zcy.edumaster.mapper")  // 扫描所有 Mapper 接口

public class EdumasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdumasterApplication.class, args);
    }

}
