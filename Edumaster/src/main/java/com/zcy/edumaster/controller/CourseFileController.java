package com.zcy.edumaster.controller;

import com.zcy.edumaster.entity.CourseFile;
import com.zcy.edumaster.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/course-files")
public class CourseFileController {

    @Autowired
    private CourseFileService courseFileService;

    // 上传课程文件信息
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadCourseFile(@RequestBody CourseFile courseFile) {
        // 保存文件信息到数据库
        boolean isSuccess = courseFileService.saveFile(courseFile);

        // 创建返回的响应体
        Map<String, Object> response = new HashMap<>();
        if (isSuccess) {
            response.put("code", HttpStatus.CREATED.value());
            response.put("message", "文件上传成功");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "文件上传失败");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getFilesByCourseId(@PathVariable Long courseId) {
        // 查询课程的文件信息
        List<CourseFile> files = courseFileService.getFilesByCourseId(courseId);

        // 如果没有找到文件信息，返回 404
        if (files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseEntityMessage(HttpStatus.NOT_FOUND.value(), "该课程没有文件信息"));
        }

        // 如果找到文件信息，返回 200 和文件信息
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    public static class ResponseEntityMessage {
        private int code;
        private String message;

        public ResponseEntityMessage(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
