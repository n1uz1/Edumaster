package com.zcy.edumaster.controller;

import com.zcy.edumaster.service.CourseRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CourseRecommendController {

    private final CourseRecommendService courseRecommendService;

    @Autowired
    public CourseRecommendController(CourseRecommendService courseRecommendService) {
        this.courseRecommendService = courseRecommendService;
    }

    @GetMapping("/CourseRecommend")
    public List<String> getCourseRecommend(@RequestParam String content) {
        return courseRecommendService.getRecommendedCourses(content);  // 获取推荐的课程
    }
}





