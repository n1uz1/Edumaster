package com.zcy.edumaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("course_lesson")
public class CourseLesson {

    @TableId(type = IdType.AUTO)
    private Long courseLessonId;  // 主键，自动生成

    private Long courseId;
    private Long lessonId;
    private String title;
    private String videoUrl;
    private String description;

    // Getters and Setters
    public Long getCourseLessonId() {
        return courseLessonId;
    }

    public void setCourseLessonId(Long courseLessonId) {
        this.courseLessonId = courseLessonId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
