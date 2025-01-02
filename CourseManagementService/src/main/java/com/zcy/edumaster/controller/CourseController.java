package com.zcy.edumaster.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;  // 用于返回 ResponseEntity 对象
import org.springframework.web.bind.annotation.GetMapping;  // 用于创建返回的 Map
import org.springframework.web.bind.annotation.PathVariable;  // 用于创建返回的 Map
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 创建课程
    @PostMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody Course course) {
        Long courseId = courseService.createCourse(course);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courseId != null) {
            response.put("code", HttpStatus.CREATED.value());
            response.put("message", "课程创建成功");
            response.put("courseId", courseId);  // 返回课程ID
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
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

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courseService.searchCoursesByTitle(title).isEmpty()) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "未找到匹配的课程");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "搜索成功");
        response.put("data", courseService.searchCoursesByTitle(title));  // 添加搜索到的课程数据

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

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courseService.getAllCourses().isEmpty()) {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "暂无课程数据");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("code", HttpStatus.OK.value());
        response.put("message", "获取所有课程成功");
        response.put("data", courseService.getAllCourses());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
