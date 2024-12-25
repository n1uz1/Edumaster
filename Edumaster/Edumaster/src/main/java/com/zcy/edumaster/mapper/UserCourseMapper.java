package com.zcy.edumaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.entity.UserCourse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCourseMapper extends BaseMapper<UserCourse> {

    // 根据用户ID查询所有课程信息，并确保课程存在
    @Select("SELECT c.* FROM user_courses uc " +
            "JOIN course c ON uc.course_id = c.course_id " +
            "WHERE uc.user_id = #{userId}")
    List<Course> findCoursesByUserId(Long userId);
}
