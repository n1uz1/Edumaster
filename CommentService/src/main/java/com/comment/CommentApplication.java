package com.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.comment.mapper") // 扫描 Mapper 接口
public class CommentApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommentApplication.class, args);
		System.out.println("服务器正在运行在 http://localhost:8081");
	}
}
