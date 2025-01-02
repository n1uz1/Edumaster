package com.techie.microservices.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {


    @Value("${comment.service.url}")
    private String commentServiceUrl;
    @Value("${course.service.url}")
    private String courseServiceUrl;
    @Value("${auth.service.url}")
    private String authServiceUrl;
    @Value("${llm.service.url}")
    private String llmServiceUrl;


    @Bean
    public RouterFunction<ServerResponse> courseServiceRoute() {
        return GatewayRouterFunctions.route("course_service")
                .route(RequestPredicates.path("/courses/**"), HandlerFunctions.http(courseServiceUrl))
                .route(RequestPredicates.path("/uploadFile/**"), HandlerFunctions.http(courseServiceUrl))
                .route(RequestPredicates.path("/users/**"), HandlerFunctions.http(courseServiceUrl))
                .route(RequestPredicates.path("/course-lessons/**"), HandlerFunctions.http(courseServiceUrl))
                .route(RequestPredicates.path("/course-files/**"), HandlerFunctions.http(courseServiceUrl))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> commentServiceRoute() {
        return GatewayRouterFunctions.route("comment_service")
                .route(RequestPredicates.path("/comments/**"), HandlerFunctions.http(commentServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return GatewayRouterFunctions.route("auth_service")
               .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http(authServiceUrl))
               .build();
    }
    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return GatewayRouterFunctions.route("llm_service")
                .route(RequestPredicates.path("/courseRecommend/**"), HandlerFunctions.http(authServiceUrl))
                .build();
    }


}
