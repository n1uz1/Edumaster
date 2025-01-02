package com.ai.llmservice.controller;

import com.ai.llmservice.service.AiKimiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kimi")
public class KimiController {
    private final AiKimiService aiKimiService;

    @Autowired
    public KimiController(AiKimiService aiKimiService) {
        this.aiKimiService = aiKimiService;
    }

    @PostMapping("/completions")
    public ResponseEntity<String> completions(@RequestParam String content) {
        try {
            String response = aiKimiService.completions(content);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}