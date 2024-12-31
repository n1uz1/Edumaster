package com.zcy.edumaster.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zcy.edumaster.entity.CourseLesson;
import com.zcy.edumaster.service.CourseLessonService;

@RestController
@RequestMapping("/course-lessons")
public class  CourseLessonController {

    @Autowired
    private CourseLessonService courseLessonService;

    // 创建课程中的某节课
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCourseLesson(@RequestBody CourseLesson courseLesson) {
        Long lessonId = courseLessonService.createCourseLesson(courseLesson);
        System.out.println(courseLesson);
        Map<String, Object> response = new HashMap<>();
        if (lessonId != null) {
            response.put("code", HttpStatus.CREATED.value());
            response.put("message", "课程节课创建成功");
            response.put("lessonId", lessonId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "课程节课创建失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 查看某个课程的某节课的详细信息
    @GetMapping("/{courseId}/{lessonId}")
    public ResponseEntity<Map<String, Object>> getCourseLessonDetails(@PathVariable Long courseId, @PathVariable Long lessonId) {
        // 获取课程节课的详细信息
        CourseLesson courseLesson = courseLessonService.getCourseLessonDetails(courseId, lessonId);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (courseLesson != null) {
            response.put("code", HttpStatus.OK.value());
            response.put("message", "查询成功");
            response.put("data", courseLesson);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程节课未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 根据 courseId 和 lessonId 删除课程节课
    @DeleteMapping("/{courseId}/{lessonId}")
    public ResponseEntity<Map<String, Object>> deleteCourseLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
        // 调用服务层删除课程节课
        boolean isSuccess = courseLessonService.deleteCourseLesson(courseId, lessonId);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (isSuccess) {
            response.put("code", HttpStatus.OK.value());
            response.put("message", "课程节课删除成功");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "课程节课未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 查看某个课程的所有课程章节
    @GetMapping("/course/{courseId}")
    public ResponseEntity<Map<String, Object>> getAllCourseLessons(@PathVariable Long courseId) {
        List<CourseLesson> courseLessons = courseLessonService.getAllCourseLessons(courseId);

        Map<String, Object> response = new HashMap<>();
        if (!courseLessons.isEmpty()) {
            response.put("code", HttpStatus.OK.value());
            response.put("message", "查询成功");
            response.put("data", courseLessons);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "该课程暂无章节信息");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
