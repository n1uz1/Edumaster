package com.zcy.edumaster.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.edumaster.entity.CourseLesson;
import com.zcy.edumaster.mapper.CourseLessonMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CourseLessonService extends ServiceImpl<CourseLessonMapper, CourseLesson> {

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    // 创建课程中的某节课
    public boolean createCourseLesson(CourseLesson courseLesson) {
        return save(courseLesson);  // 使用 MyBatis-Plus 提供的 save 方法
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
