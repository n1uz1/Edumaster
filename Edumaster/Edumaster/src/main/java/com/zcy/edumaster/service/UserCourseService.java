package com.zcy.edumaster.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.edumaster.entity.Course;
import com.zcy.edumaster.mapper.UserCourseMapper;
import org.springframework.stereotype.Service;
import com.zcy.edumaster.entity.UserCourse;

import java.util.List;

@Service
public class UserCourseService extends ServiceImpl<UserCourseMapper, UserCourse> {

    // 根据用户ID获取所有课程
    public List<Course> getCoursesByUserId(Long userId) {
        return baseMapper.findCoursesByUserId(userId);  // 调用 Mapper 层的查询方法
    }
}
