package com.ai.llmservice.service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import com.ai.llmservice.config.ApiConfig;

public class AiKimiService {
    private final String apiKey;
    private final OkHttpClient okHttpClient;

    public AiKimiService(String apiKey) {
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }

    public String completions(String content) throws IOException {
        // 构建请求体
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "moonshot-v1-8k");

        // 在content前面加上你的要求
        String requirements = "这是我的用户课程表的信息，请你根据我的用户的参与课程表，给出用户可能感兴趣的课程关键词，以json格式返回数据。\n";
        String fullContent = requirements + content;

        requestBody.put("messages", new JSONObject().put("role", "user").put("content", fullContent));
        requestBody.put("response_format", "plain");
        requestBody.put("frequency_penalty", 0.5);
        requestBody.put("stream", false);

        // 构建请求
        Request request = new Request.Builder()
                .url(ApiConfig.API_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json; charset=utf-8")))
                .addHeader("Authorization", "Bearer " + ApiConfig.API_KEY)
                .build();

        // 发送请求并获取响应
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}