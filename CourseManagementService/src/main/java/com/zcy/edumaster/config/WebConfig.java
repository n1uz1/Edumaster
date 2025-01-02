package com.zcy.edumaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置全局跨域配置
        registry.addMapping("/**")  // 允许所有路径进行跨域
                .allowedOrigins("http://localhost:8080")  // 允许的来源地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的HTTP方法
                .allowedHeaders("*")  // 允许所有请求头
                .allowCredentials(true)  // 允许携带凭证（如 Cookies）
                .maxAge(3600);  // 预检请求的有效期（秒）
    }
}

