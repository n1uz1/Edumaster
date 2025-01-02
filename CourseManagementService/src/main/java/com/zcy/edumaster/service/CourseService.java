package com.zcy.edumaster.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.mapper.CourseMapper;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Autowired
    private CourseMapper courseMapper;

    public List<Map<String, Object>> getAllCourses() {
        return courseMapper.getAllCoursesWithUsername();
    }

    // 创建课程
    public Long  createCourse(Course course) {

        // 使用 MyBatis-Plus 的 save 方法保存课程
        boolean saved = save(course);
        if (saved) {
            return course.getCourseId();  // MyBatis-Plus 会在保存后设置 ID
        }
        return null;  // 或者抛出异常
    }
   
    // 删除课程
    public boolean deleteCourse(Long courseId) {
        // 这里可以加上课程删除的相关逻辑，比如删除与用户的关联等
        return removeById(courseId);  // 使用MyBatis-Plus的removeById方法
    }

    // 更新课程信息
    public boolean updateCourse(Course course) {
        return updateById(course);  // MyBatis-Plus 提供的更新方法
    }

    // 根据课程标题搜索课程
    public List<Map<String, Object>> searchCoursesByTitle(String title) {
        return courseMapper.findCoursesByTitle(title);  // 使用 Mapper 中的查询方法
    }

    // 根据课程 ID 查询课程详情
    public Course getCourseById(Long courseId) {
        return getById(courseId);  // MyBatis-Plus 提供的 getById 方法
    }
}
