package com.zcy.edumaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.edumaster.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    // 根据课程 title 搜索课程
    @Select("SELECT * FROM course WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<Course> findCoursesByTitle(String title);

    @Select("SELECT * FROM course")
    List<Course> getAllCourses();
}
