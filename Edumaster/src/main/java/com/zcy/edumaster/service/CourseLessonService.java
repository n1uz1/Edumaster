package com.zcy.edumaster.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.edumaster.entity.CourseLesson;
import com.zcy.edumaster.mapper.CourseLessonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CourseLessonService extends ServiceImpl<CourseLessonMapper, CourseLesson> {

    @Autowired
    private CourseLessonMapper courseLessonMapper;

     
    public List<CourseLesson> getAllCourseLessons(Long courseId) {
        return courseLessonMapper.selectByCourseId(courseId);
    }    
    
    // 创建课程中的某节课
    public Long createCourseLesson(CourseLesson courseLesson) {
        boolean saved = save(courseLesson);
        if (saved) {
            return courseLesson.getLessonId();  // MyBatis-Plus 会在保存后设置 ID
        }
        return null;  // 或者抛出异常
    }

    // 根据 courseId 和 lessonId 查询课程节课的详细信息
    public CourseLesson getCourseLessonDetails(Long courseId, Long lessonId) {
        return courseLessonMapper.getCourseLessonByCourseIdAndLessonId(courseId, lessonId);
    }

    // 根据 courseId 和 lessonId 删除课程节课
    public boolean deleteCourseLesson(Long courseId, Long lessonId) {
        // 执行删除操作，返回删除成功的记录数
        int result = courseLessonMapper.deleteCourseLessonByCourseIdAndLessonId(courseId, lessonId);
        return result > 0; // 如果删除的记录数大于 0，表示删除成功
    }
}
