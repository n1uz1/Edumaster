package com.zcy.edumaster.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "LLMService", url = "http://localhost:8081/eureka/kimi")
public interface KimiClient {

    @PostMapping("/kimi/completions")
    List<String> getCompletions(@RequestParam("content") String content);  // 对应 KimiController 的 POST 方法
}



