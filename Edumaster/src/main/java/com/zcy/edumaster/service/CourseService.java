package com.zcy.edumaster.service;

import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.mapper.CourseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> getAllCourses() {
        return courseMapper.getAllCourses();
    }

    // 创建课程
    public boolean createCourse(Course course) {
        return save(course);  // 使用MyBatis-Plus的save方法
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
    public List<Course> searchCoursesByTitle(String title) {
        return baseMapper.findCoursesByTitle(title);  // 使用 Mapper 中的查询方法
    }

    // 根据课程 ID 查询课程详情
    public Course getCourseById(Long courseId) {
        return getById(courseId);  // MyBatis-Plus 提供的 getById 方法
    }
}
