package com.techie.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // 禁用 CSRF 保护
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())  // 允许所有请求无需认证
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // 配置 CORS

        // 如果不需要 OAuth2 资源服务器，可以移除下面这一行
        // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));  // 允许所有来源
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));  // 允许所有 HTTP 方法
        configuration.setAllowedHeaders(List.of("*"));  // 允许所有请求头
        configuration.setAllowCredentials(true);  // 允许携带凭证（如果需要）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 对所有路径生效
        return source;
    }
}
