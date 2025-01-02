package com.zcy.edumaster.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.edumaster.entity.Course;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    // 根据课程 title 搜索课程，并返回对应的用户表中的 username
    @Select("SELECT c.*, u.username " +
            "FROM course c " +
            "JOIN user u ON c.creator_id = u.user_id " +
            "WHERE c.title LIKE CONCAT('%', #{title}, '%')")
    List<Map<String, Object>> findCoursesByTitle(String title);


    @Select("SELECT c.*, u.username FROM course c " +
           "LEFT JOIN user u ON c.creator_id = u.user_id")
    List<Map<String, Object>> getAllCoursesWithUsername();
}
