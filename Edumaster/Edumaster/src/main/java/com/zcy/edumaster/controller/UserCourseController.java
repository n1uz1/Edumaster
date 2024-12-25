package com.zcy.edumaster.controller;

import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    // 根据 userId 查询用户所有课程
    @GetMapping("/{userId}/courses")
    public ResponseEntity<Map<String, Object>> getUserCourses(@PathVariable Long userId) {
        List<Course> courses = userCourseService.getCoursesByUserId(userId);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courses.isEmpty()) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "该用户没有选课或课程已被删除");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "查询成功");
        response.put("data", courses);  // 返回该用户的课程列表

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
