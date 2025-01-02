package com.zcy.edumaster.service;

import com.zcy.edumaster.client.KimiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zcy.edumaster.controller.CourseController;
import java.util.List;
import java.util.stream.*;
@Service
public class CourseRecommendService {

    private final KimiClient kimiClient;

    @Autowired
    private CourseController courseController;

    public List<String> getRecommendedCourses(String content) {

        String response = kimiClient.getCompletions(content);

        List<String> keywords = parseKeywordsFromResponse(response);  //

        List<String> recommendedCourses = keywords.stream()
                .map(keyword -> courseController.search(keyword))  // 调用 CourseController 的 /search 方法
                .collect(Collectors.toList());

        return recommendedCourses;
    }

    private List<String> parseKeywordsFromResponse(String response) {
        return List.of(response);
    }
}

