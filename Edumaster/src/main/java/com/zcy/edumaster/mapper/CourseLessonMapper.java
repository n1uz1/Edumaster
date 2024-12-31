package com.zcy.edumaster.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.edumaster.entity.CourseLesson;

public interface CourseLessonMapper extends BaseMapper<CourseLesson> {
    // 可以定义一些额外的自定义查询方法，如果有需要的话
    // 根据 courseId 和 lessonId 查询课程节课
    @Select("SELECT * FROM course_lesson WHERE course_id = #{courseId} AND lesson_id = #{lessonId}")
    CourseLesson getCourseLessonByCourseIdAndLessonId(@Param("courseId") Long courseId, @Param("lessonId") Long lessonId);

    // 根据 courseId 和 lessonId 删除课程节课
    @Delete("DELETE FROM course_lesson WHERE course_id = #{courseId} AND lesson_id = #{lessonId}")
    int deleteCourseLessonByCourseIdAndLessonId(@Param("courseId") Long courseId, @Param("lessonId") Long lessonId);

    @Select("SELECT * FROM course_lesson WHERE course_id = #{courseId} ORDER BY lesson_id ASC ")
    List<CourseLesson> selectByCourseId(@Param("courseId") Long courseId);
}
