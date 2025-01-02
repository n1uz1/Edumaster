package com.zcy.edumaster.controller;

import com.zcy.edumaster.utils.aliOss;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class fileUploadController {

    private final aliOss aliOss = new aliOss();

    @PostMapping("/uploadFile")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("使用alioss上传");
        if (file == null || file.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Please select a file to upload.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String fileName = file.getOriginalFilename();
            // 调用 aliOss 的 upload 方法
            String result = aliOss.uploadFile(fileName, file.getInputStream());

            Map<String, String> response = new HashMap<>();
            response.put("message", "File uploaded successfully.");
            response.put("url", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
