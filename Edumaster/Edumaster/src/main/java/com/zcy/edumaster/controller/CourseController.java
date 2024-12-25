package com.zcy.edumaster.controller;

import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.service.CourseService;
import com.zcy.edumaster.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;  // 用于返回 ResponseEntity 对象
import java.util.HashMap;  // 用于创建返回的 Map
import java.util.Map;  // 用于创建返回的 Map
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 创建课程
    @PostMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody Course course) {
        boolean isSuccess = courseService.createCourse(course);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (isSuccess) {
            response.put("code", HttpStatus.CREATED.value());
            response.put("message", "课程创建成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程创建失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Long courseId) {
        boolean isSuccess = courseService.deleteCourse(courseId);
        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (isSuccess) {
            System.out.println("zcyzcy");
            response.put("code", HttpStatus.CREATED.value());
            response.put("message", "课程删除成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 编辑课程
    @PutMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        course.setCourseId(courseId);  // 设置路径参数 courseId 到请求体中，确保更新的是正确的课程

        boolean isSuccess = courseService.updateCourse(course);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (isSuccess) {
            response.put("code", HttpStatus.OK.value());
            response.put("message", "课程更新成功");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 根据 title 搜索课程
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchCourses(@RequestParam String title) {
        List<Course> courses = courseService.searchCoursesByTitle(title);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courses.isEmpty()) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "未找到匹配的课程");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "搜索成功");
        response.put("data", courses);  // 添加搜索到的课程数据

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 根据 courseId 查询课程详情
    @GetMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseDetail(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (course == null) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "课程查询成功");
        response.put("data", course);  // 返回课程的详细信息

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 获取所有课程
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courses.isEmpty()) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "暂无课程数据");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "获取所有课程成功");
        response.put("data", courses);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
